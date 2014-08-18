#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(input.annotated.dbnsfp.ontarget.vcf)
run test.groovy input.annotated.dbnsfp.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
