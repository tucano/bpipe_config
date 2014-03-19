#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(HsMetrics_Report.tsv)
run test.groovy *.hsmetrics
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
