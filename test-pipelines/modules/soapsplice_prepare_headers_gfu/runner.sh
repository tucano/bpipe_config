#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_R1_001.fastq.header testinput_R1_002.fastq.header testinput_R2_001.fastq.header testinput_R2_002.fastq.header)
run test.groovy *.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(Sample_1/Sample_test_1_testinput_R1_001.fastq.header Sample_1/Sample_test_1_testinput_R1_002.fastq.header Sample_1/Sample_test_1_testinput_R2_001.fastq.header Sample_1/Sample_test_1_testinput_R2_002.fastq.header)
run test_dir.groovy Sample_1/*.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
