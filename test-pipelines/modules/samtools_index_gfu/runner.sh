#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.bai)
run test.groovy testinput_one.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
