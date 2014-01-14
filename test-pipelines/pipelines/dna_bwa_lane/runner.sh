#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(in_L001.merge.dedup.bam in_L001.merge.dedup.bai in_L001.merge.dedup.log in_L001.merge.dedup.metrics)

config bwa_submit_lane
runPipeLine bwa_submit_lane.groovy in_L001*.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(testinput.merge.log testinput.merge.dedup.bai testinput.merge.dedup.log testinput.merge.dedup.metrics)

config bwa_submit_lane
runPipeLine bwa_submit_lane.groovy testinput_R*_00*.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
