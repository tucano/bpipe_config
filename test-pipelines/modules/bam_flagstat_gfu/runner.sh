#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1/testinput_001.log Sample_test_2/testinput_001.log)
run test_sample_dir.groovy Sample_test_*
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(testinput_one.log)
run test.groovy testinput_one.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
