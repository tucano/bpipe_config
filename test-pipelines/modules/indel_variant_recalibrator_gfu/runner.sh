#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_R1_001.chr1.indel.plot.R testinput_R1_001.chr1.indel.recal.csv testinput_R1_001.chr1.indel.tranches)

run test.groovy testinput_R1_001.chr1.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
