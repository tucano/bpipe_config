load "../../../modules/flagstat_merge_gfu.groovy"

Bpipe.run {
    "*.bam" * [flagstat_merge_gfu]
}
