#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(HsMetrics_Report.html)
run test.groovy HsMetrics_Report.tsv
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
