#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

config genome_bam_recalibration_multi
sed 's/\/lustre1\/workspace\/Stupka\/HealthyExomes\//healty_exomes/' genome_bam_recalibration_multi.groovy | diff -p genome_bam_recalibration_multi.groovy /dev/stdin | patch 1>/dev/null
OUTPUTS=(testinput_one.indel_realigned.recalibrated.bam testinput_two.indel_realigned.recalibrated.bam)

runPipeLine genome_bam_recalibration_multi.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
