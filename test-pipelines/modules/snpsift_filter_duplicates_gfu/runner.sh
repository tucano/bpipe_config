#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_R1_001.chr1.dedup.vcf)
run test.groovy testinput_R1_001.chr1.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh


success
