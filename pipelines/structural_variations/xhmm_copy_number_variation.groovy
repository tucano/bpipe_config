about title: "XHMM: copy number variation - IOS XXX"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"
INTERVALS              = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/truseq_and_nextera_merge_for_calibration.intervals"
REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
DEPTH_DATA_DIR         = "/lustre1/workspace/Stupka/XHMM_interval_summaries/"

//--BPIPE_ENVIRONMENT_HERE--

/*
 * PIPELINE NOTES:
 */
Bpipe.run {
    set_stripe_gfu + "%.bam" * [gatk_detpth_of_coverage_gfu] + "*.sample_interval_summary" * [xhmm_merge_depth_of_coverage_gfu +
    xhmm_filter_and_target_gfu + xhmm_pca_gfu + xhmm_filter_and_normalize_gfu +
    xhmm_filter_original_data_gfu + xhmm_discover_cnv_gfu + xhmm_genotype_cnv_gfu + xhmm_fix_vcf_gfu ]
}
