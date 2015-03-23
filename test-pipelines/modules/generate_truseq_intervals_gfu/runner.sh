#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(chr1.intervals  chr10.intervals chr11.intervals chr12.intervals chr13.intervals chr14.intervals chr15.intervals chr16.intervals chr17.intervals chr18.intervals chr19.intervals)
run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

run test_no_chr.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
