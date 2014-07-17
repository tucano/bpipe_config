#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(all_samples.vcfcoverage)
run test.groovy input.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh


success
