#!/bin/bash

SCRIPT_NAME="test_split_fastq_module"
INPUTONE="../../data/testinput_R1_001.fastq.gz"
INPUTTWO="../../data/testinput_R2_001.fastq.gz"

./cleaner.sh

# SINGLE FASTQ
bpipe run test.groovy $INPUTONE
bpipe query > test.graph
RESULT=`diff expected.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for $OUTPUTONE , dependency graph"
    exit 1
fi
./cleaner.sh

# PAIR FASTQ
bpipe run test_paired.groovy $INPUTONE $INPUTTWO
bpipe query > test.graph
RESULT=`diff expected_pair.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for $OUTPUTONE , dependency graph"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0

