#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_R1_001.fastq.header testinput_R1_002.fastq.header testinput_R2_001.fastq.header testinput_R2_002.fastq.header)
run test.groovy *.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
