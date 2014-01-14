load "../../../modules/sort_and_convert_sam_gfu.groovy"

EXPERIMENT_NAME="TEST"

Bpipe.run {
    "*" * [sort_and_convert_sam_gfu.using(pretend:true)]
}
