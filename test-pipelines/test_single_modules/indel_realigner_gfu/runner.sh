#!/bin/bash

SCRIPT_NAME="test_indel_realigner_module"
INPUTBAM="../../data/testinput_one.bam"
INPUTINTERVALS="../../data/testinput_one.intervals"
OUTPUTONE="testinput_one.indel_realigned.bam"

./cleaner.sh

bpipe run test.groovy $INPUTBAM $INPUTINTERVALS
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
