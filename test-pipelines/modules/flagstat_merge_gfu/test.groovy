load "../../../modules/flagstat_merge_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
    "*.bam" * [flagstat_merge_gfu]
}
