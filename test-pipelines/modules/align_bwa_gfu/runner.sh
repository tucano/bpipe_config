#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

# SINGLE
OUTPUTS=(testinput_R1_001.sai testinput_R1_001.bam testinput_R2_001.bam testinput_R2_001.sai)

run test.groovy *.fastq
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(testinput_R1_001.fastq.sai testinput_R1_001.fastq.bam testinput_R2_001.fastq.bam testinput_R2_001.fastq.sai)

run test_compressed.groovy *.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

# PAIRED
OUTPUTS=(testinput_R1_001.sai testinput_R2_001.sai testinput_001.bam)

run test_paired.groovy testinput_R*_001.fastq
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(testinput_R1_001.fastq.sai testinput_R2_001.fastq.sai testinput_001.fastq.bam)

run test_compressed_paired.groovy testinput_R*_001.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

# FQZ
run test_fqz.groovy testinput_R*_001.fqz

success
