#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(report.tar.gz)
run test.groovy report.Rmd
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
