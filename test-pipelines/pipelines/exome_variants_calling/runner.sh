#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(all_samples.vcf_merged_and_recalibrated.dedup.vcf)
config exome_variants_calling
sed 's/\/lustre1\/workspace\/Stupka\/HealthyExomes\//healty_exomes/' exome_variants_calling.groovy | diff -p exome_variants_calling.groovy /dev/stdin | patch 1>/dev/null
runPipeLine exome_variants_calling.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
