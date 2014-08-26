#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1/testinput_one.merge.dedup.bai Sample_test_1/testinput_one.merge.dedup.bam Sample_test_1/testinput_one.merge.dedup.metrics Sample_test_2/testinput_one.merge.dedup.bai Sample_test_2/testinput_one.merge.dedup.bam Sample_test_2/testinput_one.merge.dedup.metrics)
INPUTS_TO_PRESERVE=(Sample_test_1/testinput_one.merge.bam Sample_test_2/testinput_one.merge.bam)
run test_sample_dir.groovy
checkTestOut
exists $OUTPUTS
./cleaner.sh


OUTPUTS=(testinput_one.merge.dedup.bai testinput_one.merge.dedup.bam testinput_one.merge.dedup.metrics)
run test.groovy testinput_one.merge.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
