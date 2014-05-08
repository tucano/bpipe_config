#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(vcf_called_intervals.log)
run test.groovy input.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh


success
