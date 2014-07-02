#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(BAM/flagstats.log)
run test.groovy *.merge.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
