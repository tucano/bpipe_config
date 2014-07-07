#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(read_0001.bamstats read_0002.bamstats)
run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
