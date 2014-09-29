load "../../../modules/bam_flagstat_gfu.groovy"

Bpipe.run {
    "%.bam" * [bam_flagstat_gfu]
}
