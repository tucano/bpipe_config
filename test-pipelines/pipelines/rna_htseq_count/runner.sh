#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.sorted_by_name.reads.txt testinput_one.sorted_by_name.reads_sorted.bai testinput_one.sorted_by_name.reads_sorted.bam)

config htseq_count
runPipeLine htseq_count.groovy testinput_one.bam

checkTestOut
exists $OUTPUTS
./cleaner.sh

success
