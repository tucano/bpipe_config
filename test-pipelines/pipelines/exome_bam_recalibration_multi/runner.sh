#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

config exome_bam_recalibration_multi
sed 's/\/lustre1\/workspace\/Stupka\/HealthyExomes\//healty_exomes/' exome_bam_recalibration_multi.groovy | diff -p exome_bam_recalibration_multi.groovy /dev/stdin | patch 1>/dev/null
OUTPUTS=(testinput_one.indel_realigned.recalibrated.bam testinput_two.indel_realigned.recalibrated.bam)

runPipeLine exome_bam_recalibration_multi.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
