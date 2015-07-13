load "../../../modules/rseqc_read_NVC_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
    "%" * [rseqc_read_NVC_gfu.using(pretend:true)]
}
