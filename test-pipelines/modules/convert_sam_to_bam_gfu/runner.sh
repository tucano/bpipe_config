#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_001.bam)
run test.groovy testinput_001.Aligned.out.sam
checkTestOut
exists $OUTPUTS
./cleaner.sh
success
