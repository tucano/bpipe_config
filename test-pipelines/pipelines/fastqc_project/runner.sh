#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1_report/Sample_test_1_testinput_R1_fastqc_data.txt Sample_test_1_report/Sample_test_1_testinput_R2_fastqc_data.txt)

config fastqc_project Sample_test_*
runPipeLine fastqc_project.groovy Sample_test_*
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
