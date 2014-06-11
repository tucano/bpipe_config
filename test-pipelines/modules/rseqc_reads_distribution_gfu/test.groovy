load "../../../modules/rseqc_reads_distribution_gfu.groovy"

BED12_ANNOTATION = "test"

Bpipe.run {
    "%" * [rseqc_reads_distribution_gfu.using(pretend:true)]
}
