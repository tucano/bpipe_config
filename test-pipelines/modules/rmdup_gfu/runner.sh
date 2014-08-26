#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1/testinput.merge.rmdup.bam Sample_test_2/testinput.merge.rmdup.bam)
run test_sample_dir.groovy
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(testinput.merge.rmdup.bam)

run test.groovy testinput.merge.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
