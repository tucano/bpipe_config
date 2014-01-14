#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.reads_sorted.bam)

run test.groovy testinput_one.reads.sam testinput_one.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
