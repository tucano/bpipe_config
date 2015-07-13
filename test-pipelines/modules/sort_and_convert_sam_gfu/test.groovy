load "../../../modules/sort_and_convert_sam_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

EXPERIMENT_NAME="TEST"

Bpipe.run {
    "*" * [sort_and_convert_sam_gfu.using(pretend:true)]
}
