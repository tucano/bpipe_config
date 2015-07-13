load "../../../modules/mem_bwa_gfu.groovy"
load "../../../modules/sample_dir_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

branches = [
  Sample_test_1:[
    'input/Sample_test_1/SampleSheet.csv',
    'input/Sample_test_1/Sample_test_1_testinput_R1_001.fastq.gz',
    'input/Sample_test_1/Sample_test_1_testinput_R2_001.fastq.gz',
    'input/Sample_test_1/Sample_test_1_testinput_R1_002.fastq.gz',
    'input/Sample_test_1/Sample_test_1_testinput_R2_002.fastq.gz'
  ],
  Sample_test_2:[
    'input/Sample_test_2/SampleSheet.csv',
    'input/Sample_test_2/Sample_test_2_testinput_R1_001.fastq.gz',
    'input/Sample_test_2/Sample_test_2_testinput_R2_001.fastq.gz',
    'input/Sample_test_2/Sample_test_2_testinput_R1_002.fastq.gz',
    'input/Sample_test_2/Sample_test_2_testinput_R2_002.fastq.gz'
  ]
]

Bpipe.run {
    branches * [
        sample_dir_gfu +
        "*_R*_%.fastq.gz" * [mem_bwa_gfu.using(
              pretend:true,
              paired:true,
              bwa_threads:2,
              sample_dir:true,
              use_shm: false,
              compression: "gz",
              phred_64:false
            )]
    ]
}
