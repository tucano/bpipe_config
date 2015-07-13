load "../../../modules/merge_bamtools_stats_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
    "*.bamstats" * [merge_bamtools_stats_gfu]
}
