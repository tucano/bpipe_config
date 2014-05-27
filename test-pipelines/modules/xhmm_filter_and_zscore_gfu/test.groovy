load "../../../modules/xhmm_filter_and_zscore_gfu.groovy"

PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

Bpipe.run {
    "*" * [xhmm_filter_and_zscore_gfu.using(pretend:true)]
}
