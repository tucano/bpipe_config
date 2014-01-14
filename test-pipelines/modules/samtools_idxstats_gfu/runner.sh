#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.idxstats.log testinput_two.idxstats.log)

run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
