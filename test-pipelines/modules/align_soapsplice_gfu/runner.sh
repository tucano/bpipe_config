#!/bin/bash

SCRIPT_NAME="test_align_soaplice_gfu_module"
INPUTONE="../../data/testinput_R1_001.fastq"
INPUTHEADER="../../data/testinput_R1_001.header"
INPUTTWO="../../data/testinput_R1_002.fastq.gz"
OUTPUTONE="testinput_R1_001.bam"
OUTPUTTWO="testinput_R1_002.bam"

./cleaner.sh

# SINGLE FASTQ
bpipe run test_single.groovy $INPUTONE $INPUTHEADER
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
bpipe run test_single_compressed.groovy $INPUTTWO $INPUTHEADER
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


# MULTI FASTQ
bpipe run test_multi.groovy ../../data/*.fastq ../../data/*.header
# I expect 4 files
RES=`ls *.bam | wc | awk {'print $1'}`
if [[ $RES != 4 ]]; then
    echo "Error for For multiple input fastq I expect 4 output files"
    exit 1
fi
bpipe query > test.graph
RESULT=`diff expected_multi.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for multi input, dependency graph"
    exit 1
fi
./cleaner.sh

# MULTI FASTQ.GZ
bpipe run test_multi_compressed.groovy ../../data/*.fastq.gz ../../data/*.header
RES=`ls *.bam | wc | awk {'print $1'}`
if [[ $RES != 4 ]]; then
    echo "Error for For multiple input fastq I expect 4 output files"
    exit 1
fi
bpipe query > test.graph
RESULT=`diff expected_multi_compressed.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for multi compressed input, dependency graph"
    exit 1
fi
./cleaner.sh

# PAIRED FASTQ
bpipe run test_paired.groovy ../../data/*.fastq ../../data/*.header
RES=`ls *.bam | wc | awk {'print $1'}`
if [[ $RES != 2 ]]; then
    echo "Error for For paired input fastq.gz I expect 2 output files"
    exit 1
fi
bpipe query > test.graph
RESULT=`diff expected_pair.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for multi input, dependency graph"
    exit 1
fi
./cleaner.sh

# PAIRED FASTQ.GZ
bpipe run test_paired_compressed.groovy ../../data/*.fastq.gz ../../data/*.header
RES=`ls *.bam | wc | awk {'print $1'}`
if [[ $RES != 2 ]]; then
    echo "Error for For multiple input fastq.gz I expect 2 output files"
    exit 1
fi
bpipe query > test.graph
RESULT=`diff expected_pair_compressed.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for multi input, dependency graph"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0

