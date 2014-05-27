load "../../../modules/xhmm_filter_and_normalize_gfu.groovy"

PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

Bpipe.run {
    "*" * [xhmm_filter_and_normalize_gfu.using(pretend:true)]
}
