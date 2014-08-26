#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(BAM/Sample_test_10.merge.dedup.bam doc/HsMetrics_report.tsv doc/HsMetrics_report.html)

config exome_align_project ../../../data/RUNDIRECTORY/Project_1/Sample_test_*
runPipeLine exome_align_project.groovy input.json

checkTestOut
exists $OUTPUTS
./cleaner.sh

success
