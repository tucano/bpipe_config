#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(all_samples.hsmetrics)
run test.groovy *.hsmetrics
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(all_samples.hsmetrics.tsv)
run test_rename.groovy *.hsmetrics
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
