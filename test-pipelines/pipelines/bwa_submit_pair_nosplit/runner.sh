#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_001.dedup.bai testinput_001.dedup.bam testinput_001.dedup.metrics testinput_001.dedup.log)

config bwa_submit_pair_nosplit
runPipeLine bwa_submit_pair_nosplit.groovy testinput*.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
