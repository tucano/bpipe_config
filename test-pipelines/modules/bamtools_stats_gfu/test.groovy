load "../../../modules/default_paths_gfu.groovy"
load "../../../modules/bamtools_stats_gfu.groovy"


Bpipe.run {
    "%.bam" * [bamtools_stats_gfu.using(pretend:true)]
}
