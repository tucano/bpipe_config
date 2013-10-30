#!/bin/bash

SCRIPT_NAME="test_align_bwa_gfu_module"
INPUTONE="../../data/testinput_R1_001.fastq"
INPUTTWO="../../data/testinput_R1_002.fastq.gz"

./cleaner.sh

# SINGLE FASTQ
bpipe run test.groovy $INPUTONE
bpipe query > test.graph
RESULT=`diff expected_one.graph test.graph`
if [[ $RESULT > 0 ]]; then
	echo "Error for dependency graph"
	exit 1
fi
./cleaner.sh

# SINGLE FASTQ.GZ
bpipe run test_compressed.groovy $INPUTTWO
bpipe query > test.graph
RESULT=`diff expected_two.graph test.graph`
if [[ $RESULT > 0 ]]; then
	echo "Error for dependency graph"
	exit 1
fi

./cleaner.sh

# MULTIPLE FASTQ
bpipe run test.groovy ../../data/*.fastq
bpipe query > test.graph
RESULT=`diff expected_multi.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for multi input, dependency graph"
    exit 1
fi
./cleaner.sh

# MULTIPLE FASTQ.GZ
bpipe run test_compressed.groovy ../../data/*.fastq.gz
bpipe query > test.graph
RESULT=`diff expected_multi_gz.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for multi input gz, dependency graph"
    exit 1
fi
./cleaner.sh

bpipe run test_compressed_paired.groovy ../../data/*.fastq.gz
bpipe query > test.graph
RESULT=`diff expected_multi_gz_paired.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for multi paired input gz, dependency graph"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
