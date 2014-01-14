load "../../../modules/rseqc_read_GC_gfu.groovy"

Bpipe.run {
    "%" * [rseqc_read_GC_gfu.using(pretend:true)]
}
