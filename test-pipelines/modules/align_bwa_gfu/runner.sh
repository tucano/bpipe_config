#!/bin/bash

SCRIPT_NAME="test_align_bwa_gfu_module"
INPUTONE="../../data/testinput_R1_001.fastq"
INPUTTWO="../../data/testinput_R1_002.fastq.gz"
OUTPUTONE="testinput_R1_001.sai"
OUTPUTTWO="testinput_R1_002.sai"

./cleaner.sh

# SINGLE FASTQ
bpipe run test.groovy $INPUTONE
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
bpipe run test_compressed.groovy $INPUTTWO
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

# MULTIPLE FASTQ
bpipe run test.groovy ../../data/*.fastq
# I expecd 4 files
RES=`ls *.sai | wc | awk {'print $1'}`
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

# MULTIPLE FASTQ.GZ
bpipe run test_compressed.groovy ../../data/*.fastq.gz
# I expecd 4 files
RES=`ls *.sai | wc | awk {'print $1'}`
if [[ $RES != 4 ]]; then
    echo "Error for For multiple input fastq.gz I expect 4 output files"
    exit 1
fi
bpipe query > test.graph
RESULT=`diff expected_multi_gz.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for multi input.gz, dependency graph"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"

exit 0
