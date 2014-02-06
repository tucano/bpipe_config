#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(BAM/Sample_test_10_testinput.merge.bam BAM/Sample_test_14_testinput.merge.bam BAM/Sample_test_18_testinput.merge.bam BAM/Sample_test_2_testinput.merge.bam  BAM/Sample_test_6_testinput.merge.bam BAM/Sample_test_11_testinput.merge.bam BAM/Sample_test_15_testinput.merge.bam BAM/Sample_test_19_testinput.merge.bam BAM/Sample_test_3_testinput.merge.bam  BAM/Sample_test_7_testinput.merge.bam BAM/Sample_test_12_testinput.merge.bam BAM/Sample_test_16_testinput.merge.bam BAM/Sample_test_1_testinput.merge.bam  BAM/Sample_test_4_testinput.merge.bam  BAM/Sample_test_8_testinput.merge.bam BAM/Sample_test_13_testinput.merge.bam BAM/Sample_test_17_testinput.merge.bam BAM/Sample_test_20_testinput.merge.bam BAM/Sample_test_5_testinput.merge.bam  BAM/Sample_test_9_testinput.merge.bam)

config soapsplice_submit_project ../../../data/RUNDIRECTORY/Project_1/Sample_test_*
runPipeLine soapsplice_submit_project.groovy ../../../data/RUNDIRECTORY/Project_1/Sample_test_*

checkTestOut
exists $OUTPUTS
./cleaner.sh

success
