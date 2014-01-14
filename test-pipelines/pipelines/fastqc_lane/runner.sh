#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_R1_fastqc.zip testinput_R2_fastqc.zip)

config fastqc_lane
runPipeLine fastqc_lane.groovy testinput_R1_001.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
