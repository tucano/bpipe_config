#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_R1_001.chr1.vcf)
run test.groovy testinput_R1_001.bam chr1.intervals
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
