#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

# SINGLE
OUTPUTS=(testinput_R1_fastqc.zip)
run test_single.groovy *.fastq.gz
checkTestOut
exists $OUTPUTS

./cleaner.sh

# PAIRED
OUTPUTS=(testinput_R1_fastqc.zip testinput_R2_fastqc.zip)
run test_paired.groovy *.fastq.gz
checkTestOut
exists $OUTPUTS

./cleaner.sh

success