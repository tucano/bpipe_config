#!/bin/bash

SCRIPT_NAME="test_realign_target_creator_module"
INPUT="../../data/*.fastq.gz"
OUTPUTONE="setstripe.log"

./cleaner.sh

bpipe run test.groovy $INPUT
if [[ ! -f $OUTPUTONE ]]; then
    echo "Error for $OUTPUTONE"
    exit 1
fi
bpipe query > test.graph
RESULT=`diff expected.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for $OUTPUTONE , dependency graph"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
