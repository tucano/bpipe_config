#!/bin/bash

./cleaner.sh

# SINGLE FASTQ
bpipe run test.groovy ../../data/*.fastq.gz
bpipe query > test.graph
RESULT=`diff expected.graph test.graph`
if [[ $RESULT > 0 ]]; then
	echo "Error for dependency graph"
	exit 1
fi
./cleaner.sh

bpipe run test_paired.groovy ../../data/*.fastq.gz
bpipe query > test.graph
RESULT=`diff expected_paired.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for dependency graph"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
