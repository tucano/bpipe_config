#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1/Sample_test_1_L001_R1_001_trimmed.fastq.gz Sample_test_1/Sample_test_1_L002_R1_001_trimmed.fastq.gz)

config trim_reads_single_project ../../../data/RUNDIRECTORY/Project_1/Sample_test_*
runPipeLine trim_reads_single_project.groovy input.json
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
