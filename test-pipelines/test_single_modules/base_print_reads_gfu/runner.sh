#!/bin/bash

SCRIPT_NAME="test_base_print_reads_module"
INPUTBAM="../../data/testinput_one.bam"
INPUTGRP="../../data/testinput_one.grp"
OUTPUTONE="testinput_one.recalibrated.bam"

./cleaner.sh

bpipe run test.groovy $INPUTBAM $INPUTGRP
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
