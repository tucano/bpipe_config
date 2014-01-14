#!/bin/bash
source ../../testsupport.sh
./cleaner.sh

OUTPUTS=(Sample_test_1.merge.junc Sample_test_1.merge.bam Sample_test_1.merge.bai Sample_test_1.merge.log)
config soapsplice_submit_pair
runPipeLine soapsplice_submit_pair.groovy *.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
