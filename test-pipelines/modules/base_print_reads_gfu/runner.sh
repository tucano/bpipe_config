#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.recalibrated.bam)
run test.groovy testinput_one.bam testinput_one.grp
checkTestOut
exists $OUTPUTS
./cleaner.sh

run test_multi.groovy *.bam *.grp
checkTestOut
exists $OUTPUTS
./cleaner.sh

success