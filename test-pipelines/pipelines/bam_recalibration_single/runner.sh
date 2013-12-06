#!/bin/bash

INPUTBAM=../../data/testinput_one.bam

./cleaner.sh
bpipe-config pipe bam_recalibration_single
rm bpipe.config

BPIPE_LIB="../../../modules/" && bpipe run -p test=true PI_1A_name_bam_recalibration_single.groovy $INPUTBAM
bpipe query > test.graph
RESULT=`diff expected.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for $OUTPUTONE , dependency graph"
    exit 1
fi

./cleaner.sh

echo "SUCCESS"
exit 0
