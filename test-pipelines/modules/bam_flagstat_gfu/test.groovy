load "../../../modules/default_paths_gfu.groovy"
load "../../../modules/bam_flagstat_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

Bpipe.run {
    "*" * [bam_flagstat_gfu.using(pretend:true)]
}
