#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1_report/SampleSheet.csv Sample_test_2_report/SampleSheet.csv)
run test.groovy
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
