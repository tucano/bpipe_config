#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(DATA.vcf)

config xhmm_copy_number_variation
runPipeLine xhmm_copy_number_variation.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
