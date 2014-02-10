#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput.merge.bai testinput.merge.bam)
run test.groovy testinput_*.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(TEST.merge.bai TEST.merge.bam)
run test_rename.groovy read_*.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

# SAMPLE DIR
INPUTS_TO_PRESERVE=(Sample_test_1/testinput_001.bam Sample_test_1/testinput_002.bam Sample_test_2/testinput_001.bam Sample_test_2/testinput_002.bam)
OUTPUTS=(Sample_test_1/testinput.merge.bai Sample_test_1/testinput.merge.bam Sample_test_2/testinput.merge.bai Sample_test_2/testinput.merge.bam)
run test_sample_dir.groovy Sample_test_*
checkTestOut
exists $OUTPUTS
# preserve input bams
for i in ${INPUTS_TO_PRESERVE[@]}
do
    bpipe preserve $i > /dev/null
done
./cleaner.sh

# SAMPLE DIR
INPUTS_TO_PRESERVE=(Sample_test_1/testinput_001.bam Sample_test_1/testinput_002.bam Sample_test_2/testinput_001.bam Sample_test_2/testinput_002.bam)
OUTPUTS=(Sample_test_1/Sample_test_1.merge.bai Sample_test_1/Sample_test_1.merge.bam Sample_test_2/Sample_test_2.merge.bai Sample_test_2/Sample_test_2.merge.bam)
run test_sample_dir_with_rename.groovy Sample_test_*
checkTestOut
exists $OUTPUTS
# preserve input bams
for i in ${INPUTS_TO_PRESERVE[@]}
do
    bpipe preserve $i > /dev/null
done
./cleaner.sh

success
