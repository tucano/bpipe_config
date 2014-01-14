#!/bin/bash
source ../../testsupport.sh
./cleaner.sh

OUTPUTS=(testinput_R1_001.bam testinput_R1_002.bam testinput_R2_002.bam testinput_R2_001.bam)
run test_single.groovy *.sai *.fastq
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(testinput_001.bam testinput_002.bam)
run test_paired.groovy *.sai *.fastq
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
