#!/bin/bash

SCRIPT_NAME="test_sam_bwa_module"

./cleaner.sh

bpipe run test_single.groovy ../../data/*.sai ../../data/*.fastq
# I expect 4 files
RES=`ls *.bam | wc | awk {'print $1'}`
if [[ $RES != 4 ]]; then
    echo "Error for For multiple input fastq I expect 4 output files"
    exit 1
fi
bpipe query > test.graph
RESULT=`diff expected.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for dependency graph"
    exit 1
fi
./cleaner.sh

bpipe run test_paired.groovy ../../data/*.sai ../../data/*.fastq
# I expect 4 files
RES=`ls *.bam | wc | awk {'print $1'}`
if [[ $RES != 2 ]]; then
    echo "Error for For multiple input fastq I expect 4 output files"
    exit 1
fi
bpipe query > test.graph
RESULT=`diff expected_paired.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for dependency graph"
    exit 1
fi
./cleaner.sh


echo "SUCCESS"
exit 0
