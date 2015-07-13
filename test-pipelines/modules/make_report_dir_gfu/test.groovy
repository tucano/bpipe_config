load "../../../modules/make_report_dir_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

branches = [
    Sample_test_1:[
    'Sample_test_1/SampleSheet.csv',
    'Sample_test_1/Sample_test_1_testinput_R1_001.fastq.gz',
    'Sample_test_1/Sample_test_1_testinput_R1_002.fastq.gz',
    'Sample_test_1/Sample_test_1_testinput_R2_001.fastq.gz',
    'Sample_test_1/Sample_test_1_testinput_R2_002.fastq.gz'],
    Sample_test_2:[
    'Sample_test_2/SampleSheet.csv',
    'Sample_test_2/Sample_test_2_testinput_R1_001.fastq.gz',
    'Sample_test_2/Sample_test_2_testinput_R1_002.fastq.gz',
    'Sample_test_2/Sample_test_2_testinput_R2_001.fastq.gz',
    'Sample_test_2/Sample_test_2_testinput_R2_002.fastq.gz']
]

prepare = {
    branch.sample = branch.name
}

Bpipe.run {
    branches * [ prepare + make_report_dir_gfu ]
}
