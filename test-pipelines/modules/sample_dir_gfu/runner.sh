#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1/Sample_test_1_testinput_R1_001.fastq.gz Sample_test_1/Sample_test_1_testinput_R1_002.fastq.gz Sample_test_1/Sample_test_1_testinput_R2_001.fastq.gz Sample_test_1/Sample_test_1_testinput_R2_002.fastq.gz Sample_test_1/setstripe.log Sample_test_2/setstripe.log Sample_test_2/Sample_test_2_testinput_R1_001.fastq.gz Sample_test_2_testinput_R1_002.fastq.gz Sample_test_2/Sample_test_2_testinput_R2_001.fastq.gz Sample_test_2/Sample_test_2_testinput_R2_002.fastq.gz)
run test.groovy input/Sample_test_*
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
