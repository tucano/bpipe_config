#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_R1_001.vcf)
run test.groovy *.vcf testinput_R1_001.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
