load "../../../modules/rseqc_read_NVC_gfu.groovy"

Bpipe.run {
    "%" * [rseqc_read_NVC_gfu.using(pretend:true)]
}
