#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

# SINGLE
config
OUTPUTS=(sample1_L001_R1_001.fastq.bam  sample1_L001_R1_002.fastq.sai  sample1_L001_R1_004.fastq.bam  sample1_L001_R2_001.fastq.sai  sample1_L001_R2_003.fastq.bam  sample1_L001_R2_004.fastq.sai sample1_L001_R1_001.fastq.sai  sample1_L001_R1_003.fastq.bam  sample1_L001_R1_004.fastq.sai  sample1_L001_R2_002.fastq.bam  sample1_L001_R2_003.fastq.sai sample1_L001_R1_002.fastq.bam  sample1_L001_R1_003.fastq.sai  sample1_L001_R2_001.fastq.bam  sample1_L001_R2_002.fastq.sai  sample1_L001_R2_004.fastq.bam)

run test.groovy ../../raw_data/sample1/*.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh


# PAIRED
config
OUTPUTS=(sample1_L001_R1_001.fastq.bam  sample1_L001_R1_002.fastq.bam  sample1_L001_R1_003.fastq.bam  sample1_L001_R1_004.fastq.bam  sample1_L001_R2_001.fastq.sai  sample1_L001_R2_003.fastq.sai sample1_L001_R1_001.fastq.sai  sample1_L001_R1_002.fastq.sai sample1_L001_R1_003.fastq.sai  sample1_L001_R1_004.fastq.sai  sample1_L001_R2_002.fastq.sai  sample1_L001_R2_004.fastq.sai)

run test_paired.groovy ../../raw_data/sample1/*.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
