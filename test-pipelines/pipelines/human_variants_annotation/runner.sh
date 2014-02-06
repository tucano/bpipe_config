#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Tier2.xls)

config human_variants_annotation
sed 's/\/lustre1\/workspace\/Stupka\/HealthyExomes\//healty_exomes/' human_variants_annotation.groovy | diff -p human_variants_annotation.groovy /dev/stdin | patch 1>/dev/null
runPipeLine human_variants_annotation.groovy input.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
