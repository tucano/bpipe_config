#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Tier2.xls)

config variants_annotation
sed 's/\/lustre1\/workspace\/Stupka\/HealthyExomes\//healty_exomes/' variants_annotation.groovy | diff -p variants_annotation.groovy /dev/stdin | patch 1>/dev/null
runPipeLine variants_annotation.groovy input.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
