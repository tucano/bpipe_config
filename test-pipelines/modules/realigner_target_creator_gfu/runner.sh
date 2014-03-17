#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.intervals)
run test.groovy testinput_one.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(testinput_one.intervals)
run test_genome.groovy testinput_one.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh


success
