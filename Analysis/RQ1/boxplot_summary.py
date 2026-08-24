import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import io
import re
import os

def create_plots_from_csv_file(input_path, output_dir="comparison_plots"):
    # --- 1. Load and Process Data ---
    try:
        df_long = pd.read_csv(input_path)
        print(f"Successfully loaded {len(df_long)} rows from {input_path}")
    except FileNotFoundError:
        print(f"Error: The file '{input_path}' was not found.")
        return

    # --- Extract developer median score before filtering ---
    developer_df = df_long[df_long['model'] == 'developer'].copy()
    dev_median = developer_df['final_score'].median() if not developer_df.empty else None

    # Filter out the 'developer' model to focus on LLM performance for the boxplots
    df_long = df_long[df_long['model'] != 'developer'].copy()

    # Drop any rows that might have nulls in critical columns
    df_long.dropna(subset=['prompt', 'model', 'final_score'], inplace=True)

    # --- Map model and prompt names for plotting ---
    prompt_map = {
        "prompt1": "NoHint",
        "prompt2": "Problem",
        "prompt3": "Bench.",
        "prompt4": "Both"
    }
    df_long['Prompt'] = df_long['prompt'].map(prompt_map)

    def rename_model(model_name):
        if 'gpt' in model_name.lower():
            return 'o4 mini'
        if 'gemini' in model_name.lower():
            return 'Gemini'
        if 'deepseek-r1' in model_name.lower() or model_name == 'deepseek-r1':
            return 'DS-R1'
        if 'deepseek-v3' in model_name.lower() or model_name == 'deepseek-v3':
            return 'DS-V3'
        return model_name
        
    df_long['Model'] = df_long['model'].apply(rename_model)

    # --- 2. Create Plots ---
    # Layout: (a)(b) on the first row, (c) on the second row spanning both columns.
    fig = plt.figure(figsize=(16, 12))
    gs = fig.add_gridspec(2, 4, height_ratios=[1.0, 1.0], hspace=0.35, wspace=0.3)
    axes = [fig.add_subplot(gs[0, 0:2]), fig.add_subplot(gs[0, 2:4]), fig.add_subplot(gs[1, 1:4])]
    sns.set_style("whitegrid")
    # Note: `tight_layout()` later can override spacing, so we apply `wspace`
    # after layout is tightened (see Final Touches section).

    # Define Custom Color Palettes
    # For plot1 (by Prompt, hue=Model): 4 models - Original palette
    palette_plot1 = ["#9E9EE0", "#FFF2CC", "#D3D3D3", "#90EE90"]  # OpenAI, Gemini, DeepSeek V3, DeepSeek R1
    # For plot2 (by Model, hue=Prompt): 4 prompts - Gentle pastel palette
    # Order: NoHint, Bench, Problem, Both
    palette_plot2 = ["#9E9EE0", "#FFF2CC", "#D3D3D3", "#90EE90"]

    # --- Plot 1: Prompt-Centric View ---
    ax1 = axes[0]
    prompt_order = ["NoHint", "Bench.", "Problem", "Both"]
    model_order_plot1 = ['o4 mini', 'Gemini', 'DS-V3', 'DS-R1']
    
    sns.boxplot(
        data=df_long,
        x='Prompt',
        y='final_score',
        hue='Model',
        order=prompt_order,
        hue_order=model_order_plot1,
        showfliers=False,
        ax=ax1,
        palette=palette_plot1,
        width=0.75,
        linewidth=1.2,
        boxprops=dict(edgecolor='black', linewidth=1.2),
        whiskerprops=dict(color='black', linewidth=1.2),
        capprops=dict(color='black', linewidth=1.2),
        medianprops=dict(color='black', linewidth=1.2),
    )
    
    ax1.axhline(y=1.0, color='r', linestyle='--', linewidth=1.2, label='Original')
    # Add developer median line
    if dev_median is not None:
        ax1.axhline(y=dev_median, color='blue', linestyle=':', linewidth=1.2, label='Developer Median')
    
    # ax1.set_yscale('log')
    ax1.set_title('(a) by Prompt Strategy', fontsize=19)
    ax1.set_xlabel('', fontsize=14)
    ax1.set_ylabel('Performance (pps scores)', fontsize=19)
    ax1.tick_params(axis='x', labelsize=19)
    ax1.tick_params(axis='y', labelsize=19)
    ax1.legend(loc='upper left', fontsize=13, frameon=False)
    
    # --- Print Box Plot Statistics for Plot 1 (by Prompt) ---
    print("\n" + "=" * 80)
    print("Box Plot Statistics - By Prompt Strategy")
    print("=" * 80)
    
    def calculate_box_stats(group):
        """Calculate box plot statistics for a group"""
        if len(group) == 0:
            return None
        scores_series = pd.Series(group['final_score'].values)
        q1 = scores_series.quantile(0.25)
        median = scores_series.median()
        q3 = scores_series.quantile(0.75)
        iqr = q3 - q1
        lower_fence = q1 - 1.5 * iqr
        upper_fence = q3 + 1.5 * iqr
        whisker_lower = scores_series[scores_series >= lower_fence].min()
        whisker_upper = scores_series[scores_series <= upper_fence].max()
        return {
            'count': len(scores_series),
            'min': float(scores_series.min()),
            'q1': float(q1),
            'median': float(median),
            'q3': float(q3),
            'max': float(scores_series.max()),
            'mean': float(scores_series.mean()),
            'iqr': float(iqr),
            'whisker_lower': float(whisker_lower),
            'whisker_upper': float(whisker_upper)
        }
    
    for prompt in prompt_order:
        print(f"\n--- Prompt: {prompt} ---")
        prompt_data = df_long[df_long['Prompt'] == prompt]
        for model in model_order_plot1:
            model_data = prompt_data[prompt_data['Model'] == model]
            if len(model_data) > 0:
                stats = calculate_box_stats(model_data)
                if stats:
                    print(f"  {model}:")
                    print(f"    Count: {stats['count']}")
                    print(f"    Min: {stats['min']:.4f}")
                    print(f"    Q1 (25%): {stats['q1']:.4f}")
                    print(f"    Median (50%): {stats['median']:.4f}")
                    print(f"    Q3 (75%): {stats['q3']:.4f}")
                    print(f"    Max: {stats['max']:.4f}")
                    print(f"    Mean: {stats['mean']:.4f}")
                    print(f"    IQR: {stats['iqr']:.4f}")
                    print(f"    Whisker Lower: {stats['whisker_lower']:.4f}")
                    print(f"    Whisker Upper: {stats['whisker_upper']:.4f}")

    # --- Plot 2: Model-Centric View ---
    ax2 = axes[1]
    model_order = ['o4 mini', 'Gemini', 'DS-V3', 'DS-R1']
    
    sns.boxplot(
        data=df_long,
        x='Model',
        y='final_score',
        hue='Prompt',
        order=model_order,
        hue_order=prompt_order,
        showfliers=False,
        ax=ax2,
        palette=palette_plot2,
        width=0.75,
        linewidth=1.2,
        boxprops=dict(edgecolor='black', linewidth=1.2),
        whiskerprops=dict(color='black', linewidth=1.2),
        capprops=dict(color='black', linewidth=1.2),
        medianprops=dict(color='black', linewidth=1.2),
    )

    ax2.axhline(y=1.0, color='r', linestyle='--', linewidth=1.2, label='Original')
    # Add developer median line
    if dev_median is not None:
        ax2.axhline(y=dev_median, color='blue', linestyle=':', linewidth=1.5, label='Developer Median')

    # ax2.set_yscale('log')
    ax2.set_title('(b) by Model', fontsize=19)
    ax2.set_xlabel('', fontsize=14)
    ax2.set_ylabel('')
    ax2.tick_params(axis='x', labelsize=19)
    ax2.tick_params(axis='y', labelsize=19)
    ax2.legend(loc='upper right', fontsize=13, frameon=False)
    
    # --- Print Box Plot Statistics for Plot 2 (by Model) ---
    print("\n" + "=" * 80)
    print("Box Plot Statistics - By Model")
    print("=" * 80)
    
    for model in model_order:
        print(f"\n--- Model: {model} ---")
        model_data = df_long[df_long['Model'] == model]
        for prompt in prompt_order:
            prompt_data = model_data[model_data['Prompt'] == prompt]
            if len(prompt_data) > 0:
                stats = calculate_box_stats(prompt_data)
                if stats:
                    print(f"  {prompt}:")
                    print(f"    Count: {stats['count']}")
                    print(f"    Min: {stats['min']:.4f}")
                    print(f"    Q1 (25%): {stats['q1']:.4f}")
                    print(f"    Median (50%): {stats['median']:.4f}")
                    print(f"    Q3 (75%): {stats['q3']:.4f}")
                    print(f"    Max: {stats['max']:.4f}")
                    print(f"    Mean: {stats['mean']:.4f}")
                    print(f"    IQR: {stats['iqr']:.4f}")
                    print(f"    Whisker Lower: {stats['whisker_lower']:.4f}")
                    print(f"    Whisker Upper: {stats['whisker_upper']:.4f}")

    # --- Plot 3: Taxonomy Primary Category (same figure, axes[2]) ---
    script_dir = os.path.dirname(os.path.abspath(__file__))
    taxonomy_csv = os.path.normpath(
        os.path.join(script_dir, '..', '..', 'Dataset', 'taxonomy', 'classification_results.csv')
    )
    print("\n" + "=" * 80)
    print("Box Plot Statistics - By Taxonomy Primary Category")
    print("=" * 80)

    if not os.path.exists(output_dir):
        os.makedirs(output_dir)

    tax_drawn = False
    try:
        df_tax_raw = pd.read_csv(taxonomy_csv)
    except FileNotFoundError:
        print(f"Taxonomy CSV not found at {taxonomy_csv}; skipping taxonomy panel.")
        df_tax_raw = None

    if df_tax_raw is not None:
        df_tax_raw = df_tax_raw.dropna(subset=['primary_category', 'repository', 'id'])
        df_tax_raw = df_tax_raw.drop_duplicates(subset=['repository', 'id'], keep='first')

        df_tax = pd.merge(
            df_long,
            df_tax_raw[['repository', 'id', 'primary_category']],
            left_on=['project', 'hash_value'],
            right_on=['repository', 'id'],
            how='inner',
        )
        
        # Also merge developer data with taxonomy for calculating per-category medians
        df_dev_tax = pd.merge(
            developer_df,
            df_tax_raw[['repository', 'id', 'primary_category']],
            left_on=['project', 'hash_value'],
            right_on=['repository', 'id'],
            how='inner',
        )
        
        print(f"Rows for taxonomy boxplot after merge: {len(df_tax)}")
        print(f"Developer rows with taxonomy: {len(df_dev_tax)}")

        if not df_tax.empty:
            taxonomy_order = sorted(df_tax['primary_category'].unique())
            taxonomy_labels = list(taxonomy_order)

            for cat in taxonomy_order:
                cat_data = df_tax[df_tax['primary_category'] == cat]
                if len(cat_data) > 0:
                    s = calculate_box_stats(cat_data)
                    if s is None:
                        continue
                    print(f"\n--- {cat} ---")
                    print(f"  Count: {s['count']}")
                    print(f"  Min: {s['min']:.4f}")
                    print(f"  Q1 (25%): {s['q1']:.4f}")
                    print(f"  Median (50%): {s['median']:.4f}")
                    print(f"  Q3 (75%): {s['q3']:.4f}")
                    print(f"  Max: {s['max']:.4f}")
                    print(f"  Mean: {s['mean']:.4f}")
                    print(f"  IQR: {s['iqr']:.4f}")
                    print(f"  Whisker Lower: {s['whisker_lower']:.4f}")
                    print(f"  Whisker Upper: {s['whisker_upper']:.4f}")

            n_cat = len(taxonomy_order)
            # Print developer medians per category
            print("\n--- Developer Medians by Taxonomy ---")
            for cat in taxonomy_order:
                dev_cat = df_dev_tax[df_dev_tax['primary_category'] == cat]
                if not dev_cat.empty:
                    print(f"{cat}: {dev_cat['final_score'].median():.4f} (n={len(dev_cat)})")
                else:
                    print(f"{cat}: No developer data")

            # Combine LLM and developer data for side-by-side boxplots
            df_tax_plot = df_tax[['primary_category', 'final_score']].copy()
            df_tax_plot['source'] = 'LLM'
            df_dev_plot = df_dev_tax[['primary_category', 'final_score']].copy()
            df_dev_plot['source'] = 'Developer'
            df_combined = pd.concat([df_tax_plot, df_dev_plot], ignore_index=True)
            hue_palette = {'LLM': '#9E9EE0', 'Developer': '#FFF2CC'}

            def _draw_taxonomy(ax):
                sns.boxplot(
                    data=df_combined,
                    y='primary_category',
                    x='final_score',
                    hue='source',
                    hue_order=['LLM', 'Developer'],
                    order=taxonomy_order,
                    orient='h',
                    showfliers=False,
                    ax=ax,
                    palette=hue_palette,
                    width=0.6,
                    linewidth=1.2,
                    boxprops=dict(edgecolor='black', linewidth=1.2),
                    whiskerprops=dict(color='black', linewidth=1.2),
                    capprops=dict(color='black', linewidth=1.2),
                    medianprops=dict(color='black', linewidth=1.2),
                )
                ax.axvline(x=1.0, color='r', linestyle='--', linewidth=1.2, label='Original')
                ax.set_title('(c) by Taxonomy', fontsize=19)
                ax.set_xlabel('Performance (pps scores, log scale)', fontsize=19)
                ax.set_ylabel('')
                ax.set_yticks(range(len(taxonomy_order)))
                ax.set_yticklabels(taxonomy_labels)
                ax.tick_params(axis='x', labelsize=19)
                ax.tick_params(axis='y', labelsize=19)
                ax.set_xscale('log')
                ax.set_xlim(left=0.1)
                ax.grid(axis='x', which='both', linestyle='--', alpha=0.7)
                # Merge the hue legend with the baseline line
                handles, labels = ax.get_legend_handles_labels()
                ax.legend(handles, labels, loc='lower right', fontsize=13, frameon=False)

            # Draw into the combined figure
            _draw_taxonomy(axes[2])
            tax_drawn = True

            # Also save a standalone taxonomy figure as a backup
            fig_tax, ax_tax = plt.subplots(figsize=(10, max(4.5, 0.55 * n_cat)))
            _draw_taxonomy(ax_tax)
            fig_tax.tight_layout()
            output_tax = os.path.join(output_dir, 'boxplot_taxonomy_pps.pdf')
            try:
                fig_tax.savefig(output_tax, bbox_inches='tight')
                print(f"\nSuccessfully saved taxonomy boxplot to {output_tax}")
            except Exception as e:
                print(f"Could not save taxonomy boxplot. Error: {e}")
        else:
            print("No rows after merging with taxonomy; taxonomy panel will be empty.")

    if not tax_drawn:
        axes[2].axis('off')

    # --- Final Touches: save the combined figure ---
    fig.tight_layout()

    output_filename = os.path.join(output_dir, "RQ1_performance_comparison_plot.pdf")
    try:
        fig.savefig(output_filename, bbox_inches='tight')
        print(f"Successfully saved combined plot to {output_filename}")
    except Exception as e:
        print(f"Could not save plot. Error: {e}")

    plt.show()


if __name__ == "__main__":
    # Define the path to your input CSV file
    input_csv_file = '../preprocessing/representative_benchmark_scores.csv'

    # Define the directory where the output plot will be saved
    output_plot_directory = "."

    create_plots_from_csv_file(input_csv_file, output_plot_directory)

