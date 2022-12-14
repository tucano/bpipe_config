load "../../../modules/split_fastq_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"

Bpipe.run {
    "%_R*" * [split_fastq_gfu.using(pretend:true, paired:true)]
}
