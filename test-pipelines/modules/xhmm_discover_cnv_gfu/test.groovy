load "../../../modules/xhmm_discover_cnv_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

Bpipe.run {
    "*" * [xhmm_discover_cnv_gfu.using(pretend:true)]
}
