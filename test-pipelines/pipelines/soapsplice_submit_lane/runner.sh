#!/bin/bash
source ../../testsupport.sh
./cleaner.sh


OUTPUTS=(in_L001.merge.bai in_L001.merge.bam in_L001.merge.junc in_L001.merge.log)
config soapsplice_submit_lane
runPipeLine soapsplice_submit_lane.groovy in_L001*.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
