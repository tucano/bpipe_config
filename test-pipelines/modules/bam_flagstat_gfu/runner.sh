#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Sample_test_1/testinput_001.log Sample_test_2/testinput_001.log)
INPUTS_TO_PRESERVE=(Sample_test_1/testinput_001.bam Sample_test_2/testinput_001.bam)
run test_sample_dir.groovy Sample_test_*
checkTestOut
exists $OUTPUTS
# preserve input bams
for i in ${INPUTS_TO_PRESERVE[@]}
do
    bpipe preserve $i > /dev/null
done
./cleaner.sh

OUTPUTS=(testinput_one.log)
run test.groovy testinput_one.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
