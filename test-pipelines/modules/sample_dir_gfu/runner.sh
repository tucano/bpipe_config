#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1/SampleSheet.csv Sample_test_2/SampleSheet.csv)
run test.groovy input.json
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
