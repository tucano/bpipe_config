#!/bin/bash

SCRIPT_NAME="test_mark_duplicates_module"
INPUTBAM="../../data/testinput_one.bam"
OUTPUTONE="testinput_one.grp"

./cleaner.sh

bpipe run test.groovy $INPUTBAM
bpipe query > test.graph
RESULT=`diff expected.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for $OUTPUTONE , dependency graph"
    exit 1
fi

./cleaner.sh

echo "SUCCESS"
exit 0
