#!/bin/bash

SCRIPT_NAME="test_bam_flagstat_gfu_module"
INPUTONE="../../data/testinput_one.bam"
OUTPUTONE="testinput_one.log"

./cleaner.sh

bpipe run test.groovy $INPUTONE
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
