#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(input.annotated.dbnsfp.vcf)
run test.groovy input.annotated.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
