#!/bin/bash

SCRIPT_NAME="test_base_recalibrator_module"
OUTPUTONE="testinput_one.merge.bam"
OUTPUTTWO="testinput_one.merge.bai"

./cleaner.sh

bpipe run test.groovy ../../data/*.bam
if [[ ! -f $OUTPUTONE ]]; then
    echo "Error for $OUTPUTONE"
    exit 1
fi
if [[ ! -f $OUTPUTONE ]]; then
    echo "Error for $OUTPUTTWO"
    exit 1
fi
bpipe query > test.graph
RESULT=`diff expected.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for $OUTPUTONE , dependency graph"
    exit 1
fi
./cleaner.sh

OUTPUTONE="TEST.merge.bam"
OUTPUTTWO="TEST.merge.bai"
bpipe run test_rename.groovy ../../data/*.bam
if [[ ! -f $OUTPUTONE ]]; then
    echo "Error for $OUTPUTONE"
    exit 1
fi
if [[ ! -f $OUTPUTONE ]]; then
    echo "Error for $OUTPUTTWO"
    exit 1
fi
bpipe query > test.graph
RESULT=`diff expected_rename.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for $OUTPUTONE , dependency graph"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
