#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1/testinput.merge.rmdup.bam Sample_test_2/testinput.merge.rmdup.bam)
INPUTS_TO_PRESERVE=(Sample_test_1/testinput.merge.bam Sample_test_2/testinput.merge.bam)
run test_sample_dir.groovy Sample_test_*
checkTestOut
exists $OUTPUTS
# preserve input bams
for i in ${INPUTS_TO_PRESERVE[@]}
do
    bpipe preserve $i > /dev/null
done
./cleaner.sh

OUTPUTS=(testinput.merge.rmdup.bam)

run test.groovy testinput.merge.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
