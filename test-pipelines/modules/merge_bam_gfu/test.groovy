load "../../../modules/merge_bam_gfu.groovy"

EXPERIMENT_NAME="TEST"

Bpipe.run {
    "*" * [merge_bam_gfu.using(test:true, rename: false)]
}
