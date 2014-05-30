#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_R1_001.dedup.bai testinput_R1_001.dedup.bam testinput_R1_001.dedup.metrics testinput_R1_001.dedup.log)

config bwa_submit_single_nosplit
runPipeLine bwa_submit_single_nosplit.groovy testinput*.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
