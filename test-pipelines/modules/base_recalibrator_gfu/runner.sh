#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.grp)
run test.groovy testinput_one.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(TEST_1_TEST.grp)
run test.groovy *.bam > test.out
checkTestOut
exists $OUTPUTS
./cleaner.sh

success