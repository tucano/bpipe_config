load "../../../modules/macs_call_peaks_gfu.groovy"

PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"
GSIZE            = "hs"

Bpipe.run {
    "*" * [macs_call_peaks_gfu.using(pretend:true)]
}
