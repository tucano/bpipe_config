#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput.merge.bai testinput.merge.bam)
run test.groovy testinput_*.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(TEST.merge.bai TEST.merge.bam)
run test_rename.groovy read_*.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
