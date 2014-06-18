#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1.merge.dedup.bai Sample_test_1.merge.dedup.bam Sample_test_1.merge.dedup.metrics Sample_test_1.merge.dedup.log)

config bwa_aln_submit_pair_nosplit
runPipeLine bwa_aln_submit_pair_nosplit.groovy testinput*.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
