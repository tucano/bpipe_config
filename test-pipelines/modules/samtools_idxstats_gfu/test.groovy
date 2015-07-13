load "../../../modules/samtools_idxstats_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
    "%" * [samtools_idxstats_gfu.using(pretend:true)]
}
