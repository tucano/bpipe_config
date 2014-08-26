load "../../../modules/soapsplice_prepare_headers_gfu.groovy"

CENTER          = "CTGB"
PROJECTNAME     = "PI_1A_name"
REFERENCE       = "hg19"
EXPERIMENT_NAME = "experiment"
PLATFORM        = "illumina"
REFERENCE_FAIDX = "test"

branches = [
  Sample_1:[
    'Sample_1/SampleSheet.csv',
    'Sample_1/Sample_test_1_testinput_R1_001.fastq.gz',
    'Sample_1/Sample_test_1_testinput_R2_001.fastq.gz',
    'Sample_1/Sample_test_1_testinput_R1_002.fastq.gz',
    'Sample_1/Sample_test_1_testinput_R2_002.fastq.gz'
  ]
]

prepare = {
  branch.sample = branch.name
}

Bpipe.run {
    branches * [prepare +
      "%.fastq.gz" * [soapsplice_prepare_headers_gfu.using(pretend:true,sample_dir:true)]
    ]
}
