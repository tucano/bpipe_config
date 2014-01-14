#!/bin/bash
source ../../testsupport.sh
./cleaner.sh

OUTPUTS=(Sample_test_1.merge.bai Sample_test_1.merge.bam Sample_test_1.merge.log Sample_test_1.merge.junc)
config soapsplice_submit_single

runPipeLine soapsplice_submit_single.groovy *.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
