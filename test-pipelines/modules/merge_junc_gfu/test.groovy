load "../../../modules/merge_junc_gfu.groovy"

EXPERIMENT_NAME="TEST"

Bpipe.run {
    "*" * [merge_junc_gfu.using(pretend:true)]
}
