#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Stupka_1_test_fastqc_report/index.html)

config fastqc_project ../../../data/RUNDIRECTORY/Project_1/Sample_test_*
runPipeLine fastqc_project.groovy input.json
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
