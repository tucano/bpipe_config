#!/bin/bash
source ../../testsupport.sh

./cleaner.sh
OUTPUTS=(input.snp_recalibrated.csv)
run test.groovy testinput_R1_001.chr1.vcf input.tranches input.csv
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
