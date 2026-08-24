"""
Calls the same four models used in the PerfOpt pipeline, with the same
temperature/top_p settings, adapted to read API keys from environment
variables rather than hardcoding them.

Required environment variables (set before running):
    OPENAI_API_KEY      -- for o4-mini
    DEEPSEEK_API_KEY     -- for DeepSeek-V3 and DeepSeek-R1
    GOOGLE_API_KEY       -- for Gemini 2.5 Pro
"""
import concurrent.futures
import os

PERSONA_PATH = os.path.join(os.path.dirname(os.path.abspath(__file__)), "persona.txt")
with open(PERSONA_PATH) as f:
    PERSONA = f.read()

# Hard cap on a single model call. Without this, a hung connection (server
# accepts the TCP handshake but never sends a response, or never closes the
# socket) blocks call_llm() -- and therefore the whole pipeline -- forever,
# since none of the underlying SDKs were given an explicit request timeout.
# This was observed in practice: a single stuck Gemini call once blocked all
# progress for 8+ minutes with no way to recover short of killing the process.
CALL_TIMEOUT_SECONDS = 600  # 10 minutes (bumped from 5: deepseek-r1's reasoning mode
# was hitting the old cap specifically on Hard-difficulty new60 tasks -- 12 of 15
# timeouts were Hard, suggesting genuinely long reasoning time, not a hung connection)


def _require_env(name):
    val = os.environ.get(name)
    if not val:
        raise RuntimeError(
            f"Missing required environment variable {name}. "
            f"Set it before calling this model, e.g.: export {name}=... "
        )
    return val


def improve_code_with_o4mini(prompt):
    from openai import OpenAI
    client = OpenAI(api_key=_require_env("OPENAI_API_KEY"), timeout=CALL_TIMEOUT_SECONDS)
    # NOTE: o4-mini is an OpenAI reasoning ("o-series") model. These models do
    # NOT support sampling parameters (temperature, top_p, penalties) -- passing
    # them raises a 400 "Unsupported parameter" error. Reasoning depth is
    # controlled instead via `reasoning.effort`.
    response = client.responses.create(
        model="o4-mini",
        reasoning={"effort": "medium"},
        input=[
            {"role": "system", "content": PERSONA},
            {"role": "user", "content": prompt},
        ],
    )
    return response.output_text


def improve_code_with_deepseek_v3(prompt):
    from openai import OpenAI
    client = OpenAI(api_key=_require_env("DEEPSEEK_API_KEY"), base_url="https://api.deepseek.com", timeout=CALL_TIMEOUT_SECONDS)
    response = client.chat.completions.create(
        model="deepseek-chat",
        messages=[
            {"role": "system", "content": PERSONA},
            {"role": "user", "content": prompt},
        ],
        stream=False,
        temperature=0.3,
        top_p=0.95,
    )
    return response.choices[0].message.content


def improve_code_with_deepseek_r1(prompt):
    from openai import OpenAI
    client = OpenAI(api_key=_require_env("DEEPSEEK_API_KEY"), base_url="https://api.deepseek.com", timeout=CALL_TIMEOUT_SECONDS)
    response = client.chat.completions.create(
        model="deepseek-reasoner",
        messages=[
            {"role": "system", "content": PERSONA},
            {"role": "user", "content": prompt},
        ],
        stream=False,
        temperature=0.3,
        top_p=0.95,
    )
    return response.choices[0].message.content


def improve_code_with_gemini(prompt):
    from google import genai
    from google.genai import types
    # google-genai's HttpOptions.timeout is in milliseconds.
    client = genai.Client(
        api_key=_require_env("GOOGLE_API_KEY"),
        http_options=types.HttpOptions(timeout=CALL_TIMEOUT_SECONDS * 1000),
    )
    response = client.models.generate_content(
        model="gemini-2.5-pro",
        config=types.GenerateContentConfig(
            system_instruction=PERSONA,
            temperature=0.3,
            top_p=0.95,
        ),
        contents=prompt,
    )
    return response.text


MODEL_FUNCS = {
    "o4-mini": improve_code_with_o4mini,
    "gemini": improve_code_with_gemini,
    "deepseek-v3": improve_code_with_deepseek_v3,
    "deepseek-r1": improve_code_with_deepseek_r1,
}


def call_llm(model_name, prompt):
    if model_name not in MODEL_FUNCS:
        raise ValueError(f"Unsupported model name '{model_name}'. Choose from: {list(MODEL_FUNCS)}")
    func = MODEL_FUNCS[model_name]

    # Belt-and-suspenders: enforce the timeout ourselves regardless of whether
    # the underlying SDK's own timeout setting actually takes effect (some
    # client/proxy layers silently ignore it). The worker thread may keep
    # running in the background after we give up on it -- Python cannot
    # forcibly kill a thread -- but that's fine here: we only care about not
    # blocking the rest of the pipeline.
    with concurrent.futures.ThreadPoolExecutor(max_workers=1) as executor:
        future = executor.submit(func, prompt)
        try:
            return future.result(timeout=CALL_TIMEOUT_SECONDS)
        except concurrent.futures.TimeoutError:
            raise TimeoutError(
                f"{model_name} call exceeded the {CALL_TIMEOUT_SECONDS}s hard timeout "
                f"(no response and no error from the API)."
            )
