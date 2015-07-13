load "../../../modules/rseqc_read_quality_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
    "%" * [rseqc_read_quality_gfu.using(pretend:true)]
}
