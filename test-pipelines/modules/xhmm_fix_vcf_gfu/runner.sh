#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(DATA.fixed.vcf)
run test.groovy DATA.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
