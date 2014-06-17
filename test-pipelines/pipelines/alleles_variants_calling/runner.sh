#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(all_samples.vcf)

config alleles_variants_calling
runPipeLine alleles_variants_calling.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
