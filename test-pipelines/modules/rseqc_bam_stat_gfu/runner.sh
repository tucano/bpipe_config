#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.bam_stat.log testinput_two.bam_stat.log)

run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
