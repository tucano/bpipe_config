load "../../../modules/merge_bamtools_stats_gfu.groovy"

Bpipe.run {
    "*.bamstats" * [merge_bamtools_stats_gfu]
}
