#!/bin/bash
source ../../testsupport.sh

./cleaner.sh
OUTPUTS=(testinput_R1_001.chr1.snp_recalibrated.vcf)
run test.groovy testinput_R1_001.chr1.vcf input.tranches input.csv
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
