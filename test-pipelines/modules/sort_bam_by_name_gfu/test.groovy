load "../../../modules/sort_bam_by_name_gfu.groovy"

EXPERIMENT_NAME="TEST"

Bpipe.run {
    "*" * [sort_bam_by_name_gfu.using(test:true)]
}
