#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.indel_realigned.bam)
run test.groovy testinput_one.bam testinput_one.intervals
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
