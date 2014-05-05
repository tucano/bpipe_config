#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(HsMetrics_Report.html)

config hsmetrics
runPipeLine hsmetrics.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
