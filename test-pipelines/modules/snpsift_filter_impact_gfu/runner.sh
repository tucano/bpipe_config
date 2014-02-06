#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Tier2.vcf)
run test.groovy Tier1.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh


success
