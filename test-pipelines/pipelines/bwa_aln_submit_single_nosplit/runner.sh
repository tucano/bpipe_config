#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1.merge.dedup.bai Sample_test_1.merge.dedup.bam Sample_test_1.merge.dedup.log Sample_test_1.merge.dedup.metrics )

config bwa_aln_submit_single_nosplit
runPipeLine bwa_aln_submit_single_nosplit.groovy testinput_R1_001.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
