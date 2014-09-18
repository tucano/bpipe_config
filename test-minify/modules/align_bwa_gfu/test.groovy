load "../../../modules/align_bwa_gfu.groovy"

REFERENCE_GENOME="../../genome/chr22.fa"
EXPERIMENT_NAME="test1"
PLATFORM="test"
FCID="1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

Bpipe.run {
	"%" * [align_bwa_gfu.using(paired:false,compression:"gz")]
}
