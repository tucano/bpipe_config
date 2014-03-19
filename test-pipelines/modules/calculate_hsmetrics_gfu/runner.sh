#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(read_0001.hsmetrics read_0002.hsmetrics)
run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
