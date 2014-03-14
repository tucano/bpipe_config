#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_single/Sample_test_1_TAAGGCGA-TAGATCGC_L001_R1_fastqc_data.txt Sample_single/Sample_test_1_TAAGGCGA-TAGATCGC_L002_R1_fastqc_data.txt)
run test_single.groovy Sample_single
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(Sample_test_2_report/Sample_test_2_testinput_R1_fastqc_data.txt Sample_test_2_report/Sample_test_2_testinput_R2_fastqc_data.txt)
run test_paired.groovy Sample_test_2_report
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(Sample_test_1_report/Sample_test_1_TAAGGCGA-TAGATCGC_L001_R1_fastqc_data.txt Sample_test_1_report/Sample_test_1_TAAGGCGA-TAGATCGC_L001_R2_fastqc_data.txt Sample_test_1_report/Sample_test_1_TAAGGCGA-TAGATCGC_L002_R1_fastqc_data.txt Sample_test_1_report/Sample_test_1_TAAGGCGA-TAGATCGC_L002_R2_fastqc_data.txt)
run test_paired.groovy Sample_test_1_report
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
