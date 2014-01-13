#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.reads.sam testinput_one.reads.txt)
run test.groovy testinput_one.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success