#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_10/Sample_test_10_L001_R1_001_paired.fastq.gz Sample_test_10/Sample_test_10_L001_R2_001_paired.fastq.gz)

config trim_reads_project ../../../data/RUNDIRECTORY/Project_1/Sample_test_*
runPipeLine trim_reads_project.groovy ../../../data/RUNDIRECTORY/Project_1/Sample_test_*
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
