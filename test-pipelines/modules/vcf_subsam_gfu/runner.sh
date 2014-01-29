#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(input.filtered.vcf)
run test.groovy input.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
