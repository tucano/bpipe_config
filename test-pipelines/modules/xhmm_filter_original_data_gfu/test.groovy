load "../../../modules/xhmm_filter_original_data_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

Bpipe.run {
    "*" * [xhmm_filter_original_data_gfu.using(pretend:true)]
}
