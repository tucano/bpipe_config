#!/bin/bash

source ../../testsupport.sh

./cleaner.sh
config
OUTPUTS=(sample1_L001_001.log sample1_L001_002.log sample1_L001_003.log sample1_L001_004.log)
run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

config
OUTPUTS=(Sample_test_2/sample2.log Sample_test_1/sample1.log)
run test_sample_dir.groovy
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
