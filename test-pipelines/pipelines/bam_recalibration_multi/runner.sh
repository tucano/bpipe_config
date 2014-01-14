#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

config bam_recalibration_multi

OUTPUTS=(testinput_one.indel_realigned.recalibrated.bam testinput_two.indel_realigned.recalibrated.bam)

runPipeLine bam_recalibration_multi.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
