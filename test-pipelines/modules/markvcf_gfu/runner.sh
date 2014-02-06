#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Tier1.vcf)
run test.groovy Tier0.quality.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh


success
