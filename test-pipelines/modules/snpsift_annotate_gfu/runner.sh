#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(input.annotated.vcf)
run test.groovy input.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
