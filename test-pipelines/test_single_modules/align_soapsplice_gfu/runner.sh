#!/bin/bash

SCRIPT_NAME="test_align_bwa_gfu_module"
INPUTONE="../../data/testinput_R1_001.fastq"
INPUTTWO="../../data/testinput_R1_002.fastq.gz"
OUTPUTONE="testinput_R1_001.bam"
OUTPUTTWO="testinput_R1_002.bam"

./cleaner.sh

# SINGLE FASTQ
bpipe run test_single.groovy $INPUTONE
if [[ ! -f $OUTPUTONE ]]; then
    echo "Error for $OUTPUTONE"
    exit 1
fi
bpipe query > test.graph
RESULT=`diff expected_one.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for $OUTPUTONE , dependency graph"
    exit 1
fi
./cleaner.sh

# SINGLE FASTQ.GZ
bpipe run test_single.groovy $INPUTTWO
if [[ ! -f $OUTPUTTWO ]]; then
    echo "Error for $OUTPUTTWO"
    exit 1
fi
bpipe query > test.graph
RESULT=`diff expected_two.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for $OUTPUTTWO , dependency graph"
    exit 1
fi
./cleaner.sh

# PAIRED FASTQ.GZ
bpipe run test_paired.groovy ../../data/*.fastq.gz
# I expecd 4 files
RES=`ls *.bam | wc | awk {'print $1'}`
if [[ $RES != 2 ]]; then
    echo "Error for For multiple input fastq.gz I expect 2 output files"
    exit 1
fi
bpipe query > test.graph
RESULT=`diff expected_multi.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for multi input, dependency graph"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0

