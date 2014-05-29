#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

# SINGLE
OUTPUTS=(testinput_R1_001.bam testinput_R1_002.bam	testinput_R2_001.bam testinput_R2_002.bam)
run test_single.groovy *.fastq
checkTestOut
exists $OUTPUTS
./cleaner.sh

run test_single_compressed.groovy *.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

# PAIRED
OUTPUTS=(testinput_001.bam testinput_002.bam)
run test_paired.groovy *.fastq
checkTestOut
exists $OUTPUTS
./cleaner.sh

run test_paired_compressed.groovy *.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

# CHANGE DIR
OUTPUTS=(Sample_test_1/Sample_test_1_testinput_001.bam Sample_test_1/Sample_test_1_testinput_002.bam Sample_test_2/Sample_test_2_testinput_001.bam Sample_test_2/Sample_test_2_testinput_002.bam)
run test_paired_sample_dir.groovy input/Sample_test_*
checkTestOut
exists $OUTPUTS
./cleaner.sh

# /DEV/SHM
OUTPUTS=(testinput_001.bam testinput_002.bam)
run test_paired_shm.groovy *.fastq
checkTestOut
exists $OUTPUTS
./cleaner.sh

# FQZ
OUTPUTS=(testinput_001.bam testinput_002.bam)
run test_paired_fqz.groovy *.fqz
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(testinput_001.bam testinput_002.bam)
run test_paired_fqz_phred.groovy *.fqz
checkTestOut
exists $OUTPUTS
./cleaner.sh

# PHRED 64
OUTPUTS=(testinput_001.bam testinput_002.bam)
run test_paired_phred64.groovy *.fastq
checkTestOut
exists $OUTPUTS
./cleaner.sh


success
