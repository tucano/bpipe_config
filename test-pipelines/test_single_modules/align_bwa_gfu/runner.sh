#!/bin/bash

SCRIPT_NAME="test_align_bwa_gfu_module"
INPUTONE="../../data/testinput_R1_001.fastq"
INPUTTWO="../../data/testinput_R1_002.fastq.gz"
OUTPUTONE="testinput_R1_001.sai"
OUTPUTTWO="testinput_R1_002.sai"

./cleaner.sh

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

bpipe run test.groovy $INPUTTWO

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

echo "SUCCESS"

exit 0
