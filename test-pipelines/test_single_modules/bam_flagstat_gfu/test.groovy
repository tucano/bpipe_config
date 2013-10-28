load "../../../modules/bam_flagstat_gfu.groovy"

Bpipe.run {
    "*" * [bam_flagstat_gfu.using(test:true)]
}
