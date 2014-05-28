#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(in_L001_R1_001.fastq.merge.dedup.bam)

config bwa_aln_submit_lane
runPipeLine bwa_aln_submit_lane.groovy in_L001*.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
