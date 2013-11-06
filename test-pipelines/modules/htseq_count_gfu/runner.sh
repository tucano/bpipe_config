#!/bin/bash

SCRIPT_NAME="test_htseq_count_module"

./cleaner.sh

bpipe run test.groovy ../../data/testinput_one.bam
bpipe query > test.graph
RESULT=`diff expected.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for $OUTPUTONE , dependency graph"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0