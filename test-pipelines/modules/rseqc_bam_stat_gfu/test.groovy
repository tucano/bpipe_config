load "../../../modules/rseqc_bam_stat_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
    "%" * [rseqc_bam_stat_gfu.using(pretend:true)]
}
