#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.qual.boxplot.pdf testinput_one.qual.r testinput_two.qual.boxplot.pdf testinput_two.qual.r)

run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
