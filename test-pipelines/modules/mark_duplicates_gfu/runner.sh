#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.merge.dedup.bai testinput_one.merge.dedup.bam testinput_one.merge.dedup.metrics)

run test.groovy testinput_one.merge.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success