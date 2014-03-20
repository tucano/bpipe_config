#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(MERGED_BAM/testinput_002.bam MERGED_BAM/testinput_001.bam)
run test.groovy Sample_test_*/*.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
