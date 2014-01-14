#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

# SINGLE
OUTPUTS=(read_1.fastq)
run test.groovy testinput_R1_001.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

# PAIR FASTQ
OUTPUTS=(read1_1.fastq read2_1.fastq)
run test_paired.groovy testinput_R1_001.fastq.gz testinput_R2_001.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
