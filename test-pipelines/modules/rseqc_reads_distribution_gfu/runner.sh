#!/bin/bash
source ../../testsupport.sh

./cleaner.sh
OUTPUTS=(testinput_one.reads_distribution.log testinput_two.reads_distribution.log)

run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
