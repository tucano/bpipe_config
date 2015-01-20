#!/bin/bash
source ../../testsupport.sh
./cleaner.sh

OUTPUTS=(Sample_test_1.merge.bai Sample_test_1.merge.bam)
config star_submit_single

runPipeLine star_submit_single.groovy *.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
