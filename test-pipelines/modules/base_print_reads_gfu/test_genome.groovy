load "../../../modules/base_print_reads_gfu.groovy"

REFERENCE_GENOME_FASTA = "/test/reference/pippo.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

Bpipe.run {
    "*" * [base_print_reads_gfu.using(pretend:true,target_intervals:false)]
}
