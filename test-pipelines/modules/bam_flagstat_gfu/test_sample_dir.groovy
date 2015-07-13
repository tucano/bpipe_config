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

branches = [
    Sample_test_1:['Sample_test_1/testinput_001.bam'],
    Sample_test_2:['Sample_test_2/testinput_002.bam']
]

prepare = {
    branch.sample = branch.name
    forward input.bam
}

Bpipe.run {
    branches * [
        prepare + "*.bam" * [bam_flagstat_gfu.using(pretend:true,sample_dir:true)]
    ]
}
