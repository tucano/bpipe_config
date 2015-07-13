load "../../../modules/align_star_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"
STAR="STAR"

Bpipe.run {
    "_R*_%.fastq" * [align_star_gfu.using(pretend:true,paired:false,compression:"")]
}
