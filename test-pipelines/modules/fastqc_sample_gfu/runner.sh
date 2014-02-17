#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1_report/Sample_test_1_testinput_R1_fastqc.zip)
run test_single.groovy Sample_test_1_report
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(Sample_test_2_report/Sample_test_2_testinput_R1_fastqc.zip Sample_test_2_report/Sample_test_2_testinput_R2_fastqc.zip)
run test_paired.groovy Sample_test_2_report
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
