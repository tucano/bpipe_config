#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.vcf_merged_and_recalibrated.dedup.vcf)

config exome_variants_calling
runPipeLine exome_variants_calling.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success