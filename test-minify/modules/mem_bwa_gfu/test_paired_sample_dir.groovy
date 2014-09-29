load "../../../modules/mem_bwa_gfu.groovy"
load "../../../modules/sample_dir_gfu.groovy"

REFERENCE_GENOME="../../genome/chr22.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

branches = [
  sample1:[
    '../../raw_data/sample1/SampleSheet.csv',
    '../../raw_data/sample1/sample1_L001_R1_001.fastq.gz',
    '../../raw_data/sample1/sample1_L001_R2_001.fastq.gz',
    '../../raw_data/sample1/sample1_L001_R1_002.fastq.gz',
    '../../raw_data/sample1/sample1_L001_R2_002.fastq.gz',
    '../../raw_data/sample1/sample1_L001_R1_003.fastq.gz',
    '../../raw_data/sample1/sample1_L001_R2_003.fastq.gz',
    '../../raw_data/sample1/sample1_L001_R1_004.fastq.gz',
    '../../raw_data/sample1/sample1_L001_R2_004.fastq.gz'
  ],
  sample2:[
    '../../raw_data/sample2/SampleSheet.csv',
    '../../raw_data/sample2/sample2_L001_R1_001.fastq.gz',
    '../../raw_data/sample2/sample2_L001_R2_001.fastq.gz',
    '../../raw_data/sample2/sample2_L001_R1_002.fastq.gz',
    '../../raw_data/sample2/sample2_L001_R2_002.fastq.gz',
    '../../raw_data/sample2/sample2_L001_R1_003.fastq.gz',
    '../../raw_data/sample2/sample2_L001_R2_003.fastq.gz',
    '../../raw_data/sample2/sample2_L001_R1_004.fastq.gz',
    '../../raw_data/sample2/sample2_L001_R2_004.fastq.gz'
  ]
]

Bpipe.run {
    branches * [
        sample_dir_gfu +
        "*_R*_%.fastq.gz" * [mem_bwa_gfu.using(
              pretend:false,
              paired:true,
              bwa_threads:2,
              sample_dir:true,
              use_shm: false,
              compression: "gz",
              phred_64:false
            )]
    ]
}
