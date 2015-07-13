load "../../../modules/xhmm_merge_depth_of_coverage_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

REFERENCE_GENOME_FASTA = "/lustre1/genomes/hg19/fa/hg19.fa"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"
DEPTH_DATA_DIR         = "data_dir"


Bpipe.run {
    "*.sample_interval_summary" * [xhmm_merge_depth_of_coverage_gfu.using(pretend:true)]
}
