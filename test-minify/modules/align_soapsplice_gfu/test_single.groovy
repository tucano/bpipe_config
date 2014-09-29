load "../../../modules/align_soapsplice_gfu.groovy"

REFERENCE_GENOME = "../../genome/chr22.fa.index"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

Bpipe.run {
    "%.gz" * [align_soapsplice_gfu.using(paired:false,compression:"gz")]
}
