#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.vcf_merged_and_recalibrated.dedup.vcf)

config human_variants_calling
runPipeLine human_variants_calling.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
