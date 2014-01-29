#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Tier0.vcf)
run test.groovy input.annotated.dbnsfp.ontarget.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
