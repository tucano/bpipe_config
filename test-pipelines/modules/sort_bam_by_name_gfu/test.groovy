load "../../../modules/sort_bam_by_name_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

EXPERIMENT_NAME="TEST"

Bpipe.run {
    "*" * [sort_bam_by_name_gfu.using(pretend:true)]
}
