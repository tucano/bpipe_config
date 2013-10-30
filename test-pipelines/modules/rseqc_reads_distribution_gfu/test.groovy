load "../../../modules/rseqc_reads_distribution_gfu.groovy"

Bpipe.run {
    "%" * [rseqc_reads_distribution_gfu.using(test:true)]
}
