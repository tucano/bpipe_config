#!/bin/bash
source ../../testsupport.sh
./cleaner.sh

OUTPUTS=(Sample_test_1.merge.bai Sample_test_1.merge.bam)
config star_submit_pair

runPipeLine star_submit_pair.groovy *.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
