load "../../../modules/samtools_index_gfu.groovy"

EXPERIMENT_NAME="TEST"

Bpipe.run {
    "*" * [samtools_index_gfu.using(test:true)]
}
