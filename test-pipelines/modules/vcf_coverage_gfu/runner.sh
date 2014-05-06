#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(vcf_coverage.log)
run test.groovy input.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh


success
