#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(report.html report.Rmd)

config WES_report
sed 's/\/lustre1\/tools\/libexec\/bpipeconfig\//..\/..\/..\//' WES_report.groovy | diff -p WES_report.groovy /dev/stdin | patch 1>/dev/null
runPipeLine WES_report.groovy BAM/*.bam VCF/*.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
