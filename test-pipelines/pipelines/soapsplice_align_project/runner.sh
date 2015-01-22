#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(BAM/Sample_test_10.merge.bam)

config soapsplice_align_project ../../../data/RUNDIRECTORY/Project_1/Sample_test_*
runPipeLine soapsplice_align_project.groovy input.json

checkTestOut
exists $OUTPUTS
./cleaner.sh

success
