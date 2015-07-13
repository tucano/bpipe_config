load "../../../modules/samtools_index_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

EXPERIMENT_NAME="TEST"

Bpipe.run {
    "*" * [samtools_index_gfu.using(pretend:true)]
}
