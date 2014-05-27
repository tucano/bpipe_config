#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(DATA.fixed.vcf)

config xhmm_copy_number_variation
sed 's/\/lustre1\/workspace\/Stupka\/XHMM_interval_summaries\//data_dir/' xhmm_copy_number_variation.groovy | diff -p xhmm_copy_number_variation.groovy /dev/stdin | patch 1>/dev/null
runPipeLine xhmm_copy_number_variation.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
