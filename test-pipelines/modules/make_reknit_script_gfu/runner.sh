#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(reknit.groovy)
run test.groovy report.Rmd
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
