#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Tier0.quality.vcf)
run test.groovy Tier0.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh


success
