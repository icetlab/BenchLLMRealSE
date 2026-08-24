"""
Parses an LLM's JSON search/replace output (same format as the real PerfOpt
pipeline's persona.txt) and applies it to a copy of a task's Original Solution.java.

Format expected from the LLM (after an optional free-text explanation):
[
  {"filepath": "...", "search": "...", "replace": "..."},
  ...
]

Since every task here is a single Solution.java file (not a multi-file real repo),
"filepath" is not used to route the change -- every block is applied to the same
file, in order. This mirrors PerfOpt's apply_llm_changes.py logic (extract the
JSON array, apply each search/replace atomically, fail with "[Format Error]" if
anything doesn't parse or a search block can't be found).
"""
import json
import re


class PatchFormatError(Exception):
    pass


def _extract_json_array(llm_output):
    """Find the first top-level JSON array in the LLM's raw text output."""
    # Prefer a fenced ```json ... ``` block if present.
    fence_match = re.search(r"```json\s*(\[.*?\])\s*```", llm_output, re.DOTALL)
    candidate = fence_match.group(1) if fence_match else None

    if candidate is None:
        # Fall back to the first '[' ... matching ']' at bracket-depth 0.
        start = llm_output.find("[")
        if start == -1:
            raise PatchFormatError("No JSON array found in LLM output.")
        depth = 0
        end = None
        in_string = False
        escape = False
        for i in range(start, len(llm_output)):
            ch = llm_output[i]
            if in_string:
                if escape:
                    escape = False
                elif ch == "\\":
                    escape = True
                elif ch == '"':
                    in_string = False
                continue
            if ch == '"':
                in_string = True
            elif ch == "[":
                depth += 1
            elif ch == "]":
                depth -= 1
                if depth == 0:
                    end = i + 1
                    break
        if end is None:
            raise PatchFormatError("Unterminated JSON array in LLM output.")
        candidate = llm_output[start:end]

    try:
        blocks = json.loads(candidate)
    except json.JSONDecodeError as e:
        raise PatchFormatError(f"JSON did not parse: {e}")

    if not isinstance(blocks, list) or not blocks:
        raise PatchFormatError("Parsed JSON is not a non-empty array.")
    for b in blocks:
        if not isinstance(b, dict) or "search" not in b or "replace" not in b:
            raise PatchFormatError("Each block must be an object with 'search' and 'replace'.")
    return blocks


def apply_patch(original_source, llm_output):
    """
    original_source: the Original Solution.java text (str)
    llm_output: raw text returned by the LLM

    Returns the patched source (str).
    Raises PatchFormatError if the output doesn't parse, or a search block
    cannot be found (uniquely or at all) in the current source.
    """
    blocks = _extract_json_array(llm_output)

    patched = original_source
    for i, block in enumerate(blocks):
        search = block["search"]
        replace = block["replace"]
        count = patched.count(search)
        if count == 0:
            raise PatchFormatError(
                f"Block {i}: search text not found in source.\nsearch={search!r}"
            )
        if count > 1:
            raise PatchFormatError(
                f"Block {i}: search text is ambiguous ({count} occurrences).\nsearch={search!r}"
            )
        patched = patched.replace(search, replace, 1)

    return patched


if __name__ == "__main__":
    # Minimal self-test.
    original = "class Solution {\n    int slow() { return 1 + 1; }\n}\n"
    llm_output = '''Explanation: simplify the addition.
```json
[
  {"filepath": "Solution.java", "search": "int slow() { return 1 + 1; }", "replace": "int slow() { return 2; }"}
]
```'''
    patched = apply_patch(original, llm_output)
    print(patched)
    assert "return 2;" in patched
    print("Self-test OK")
