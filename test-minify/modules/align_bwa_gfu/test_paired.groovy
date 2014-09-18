load "../../../modules/align_bwa_gfu.groovy"

REFERENCE_GENOME="../../genome/chr22.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

run {
	"_R*_%." * [align_bwa_gfu.using(paired:true, compression:"gz")]
}
