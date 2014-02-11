load "../../../modules/move_sample_results.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

Bpipe.run {
    "*" * [ move_sample_results.using(result_dir:"MERGED_BAM",pretend:true) ]
}
