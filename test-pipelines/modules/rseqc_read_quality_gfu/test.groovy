load "../../../modules/rseqc_read_quality_gfu.groovy"

Bpipe.run {
    "%" * [rseqc_read_quality_gfu.using(pretend:true)]
}
