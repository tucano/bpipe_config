load "../../../modules/align_bwa_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"
EXPERIMENT_NAME="test1"
PLATFORM="test"
FCID="1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

Bpipe.run {
	"%" * [align_bwa_gfu.using(pretend:true,paired:false,compression:"")]
}
