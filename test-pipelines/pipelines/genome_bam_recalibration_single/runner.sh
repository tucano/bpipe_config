#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

config genome_bam_recalibration_single

OUTPUTS=(testinput_one.indel_realigned.recalibrated.bam)

runPipeLine genome_bam_recalibration_single.groovy testinput_one.bam

checkTestOut
exists $OUTPUTS
./cleaner.sh

success
