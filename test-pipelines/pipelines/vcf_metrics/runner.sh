#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(vcf_coverage.log vcf_called_intervals.log)

config vcf_metrics
runPipeLine vcf_metrics.groovy input.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
