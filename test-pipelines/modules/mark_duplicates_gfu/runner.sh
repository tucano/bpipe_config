#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1/testinput_one.merge.dedup.bai Sample_test_1/testinput_one.merge.dedup.bam Sample_test_1/testinput_one.merge.dedup.metrics Sample_test_2/testinput_one.merge.dedup.bai Sample_test_2/testinput_one.merge.dedup.bam Sample_test_2/testinput_one.merge.dedup.metrics)
INPUTS_TO_PRESERVE=(Sample_test_1/testinput_one.merge.bam Sample_test_2/testinput_one.merge.bam)
run test_sample_dir.groovy Sample_test_*
checkTestOut
exists $OUTPUTS
# preserve input bams
for i in ${INPUTS_TO_PRESERVE[@]}
do
    bpipe preserve $i > /dev/null
done
./cleaner.sh


OUTPUTS=(testinput_one.merge.dedup.bai testinput_one.merge.dedup.bam testinput_one.merge.dedup.metrics)
run test.groovy testinput_one.merge.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
