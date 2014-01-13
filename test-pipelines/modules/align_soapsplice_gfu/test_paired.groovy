load "../../../modules/align_soapsplice_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

Bpipe.run {
    "_R*_%.fastq" * [align_soapsplice_gfu.using(pretend:true,paired:true,compressed:false)]
}
