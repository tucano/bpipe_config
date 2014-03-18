#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(in_L001.merge.sorted_by_name.reads.txt in_L001.merge.sorted_by_name.reads_sorted.bam)

config rna_seq_lane
runPipeLine rna_seq_lane.groovy in_L001*.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success

