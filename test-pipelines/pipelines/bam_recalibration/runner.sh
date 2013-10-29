#!/bin/bash

SCRIPT_NAME="test_bam_recalibration_pipeline"
INPUTBAM="../../data/testinput_one.bam"
OUTPUTONE="testinput_one.indel_realigned.recalibrated.bam"

./cleaner.sh

BPIPE_LIB="../../../modules/" && bpipe run test.groovy $INPUTBAM
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
