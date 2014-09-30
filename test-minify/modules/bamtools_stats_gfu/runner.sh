#!/bin/bash

source ../../testsupport.sh

./cleaner.sh
config
OUTPUTS=(sample1_L001_001.bamstats sample1_L001_002.bamstats sample1_L001_003.bamstats sample1_L001_004.bamstats)
run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

cleanBpipeDir

success
