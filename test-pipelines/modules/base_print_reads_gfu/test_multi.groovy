load "../../../modules/default_paths_gfu.groovy"
load "../../../modules/base_print_reads_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"
REFERENCE_GENOME_FASTA = "/test/reference/pippo.fa"
INTERVALS              = "/test/interls.intervals"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

Bpipe.run {
    "%.*" * [base_print_reads_gfu.using(pretend:true,target_intervals:true)]
}
