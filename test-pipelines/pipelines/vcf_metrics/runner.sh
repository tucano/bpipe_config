#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(all_samples.vcfcoverage vcf_called_intervals.log)

config vcf_metrics
runPipeLine vcf_metrics.groovy input.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
