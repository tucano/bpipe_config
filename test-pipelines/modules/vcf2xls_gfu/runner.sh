#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Tier2.xls)
run test.groovy Tier2.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh


success
