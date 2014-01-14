#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1_report.zip Sample_test_2_report.zip)

config fastqc_project Sample_test_*
runPipeLine fastqc_project.groovy Sample_test_*
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
