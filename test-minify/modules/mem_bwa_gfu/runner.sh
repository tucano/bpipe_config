#!/bin/bash

source ../../testsupport.sh

./cleaner.sh
config

# SINGLE
OUTPUTS=(sample1_L001_R1_001.bam sample1_L001_R1_002.bam sample1_L001_R1_003.bam sample1_L001_R1_004.bam sample1_L001_R2_001.bam sample1_L001_R2_002.bam sample1_L001_R2_003.bam sample1_L001_R2_004.bam)
run test_single.groovy ../../raw_data/sample1_fastq/*.fastq
checkTestOut
exists $OUTPUTS
./cleaner.sh

config
run test_single_compressed.groovy ../../raw_data/sample1/*.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

# PAIRED
config
OUTPUTS=(sample1_L001_001.bam sample1_L001_002.bam sample1_L001_003.bam sample1_L001_004.bam)
run test_paired.groovy ../../raw_data/sample1_fastq/*.fastq
checkTestOut
exists $OUTPUTS
./cleaner.sh

config
run test_paired_compressed.groovy ../../raw_data/sample1/*.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

# CHANGE DIR
config
OUTPUTS=(sample1/sample1_L001_001.bam sample1/sample1_L001_002.bam sample1/sample1_L001_003.bam sample1/sample1_L001_004.bam sample1/SampleSheet.csv sample1/setstripe.log)
run test_paired_sample_dir.groovy
checkTestOut
exists $OUTPUTS
./cleaner.sh

# /DEV/SHM
config
OUTPUTS=(sample1_L001_001.bam sample1_L001_002.bam sample1_L001_003.bam sample1_L001_004.bam)
run test_paired_shm.groovy ../../raw_data/sample1_fastq/*.fastq
checkTestOut
exists $OUTPUTS
./cleaner.sh

# FQZ
run test_paired_fqz.groovy ../../raw_data/sample1_fqz/*.fqz
checkTestOut
exists $OUTPUTS
./cleaner.sh

config
run test_paired_fqz_phred.groovy ../../raw_data/sample1_fqz/*.fqz
checkTestOut
exists $OUTPUTS
./cleaner.sh

# PHRED 64
run test_paired_phred64.groovy ../../raw_data/sample1_fastq/*.fastq
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
