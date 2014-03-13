#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(all_samples.vcf_merged_and_recalibrated.dedup.vcf)

config genome_variants_calling
runPipeLine genome_variants_calling.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
