load "../../../modules/split_fastq_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"

Bpipe.run {
    "%" * [split_fastq_gfu.using(test:true, paired:false)]
}
