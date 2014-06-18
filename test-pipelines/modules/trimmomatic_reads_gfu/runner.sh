#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

# SINGLE
OUTPUTS=(testinput_R1_001_paired.fastq.gz testinput_R1_001_unpaired.fastq.gz)
run test.groovy testinput_R1_001.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

# PAIR FASTQ
OUTPUTS=(testinput_R1_001_paired.fastq.gz testinput_R1_001_unpaired.fastq.gz testinput_R2_001_paired.fastq.gz testinput_R2_001_unpaired.fastq.gz)
run test_paired.groovy testinput_R1_001.fastq.gz testinput_R2_001.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
