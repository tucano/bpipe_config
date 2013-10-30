load "../../../modules/rseqc_bam_stat_gfu.groovy"

Bpipe.run {
    "%" * [rseqc_bam_stat_gfu.using(test:true)]
}
