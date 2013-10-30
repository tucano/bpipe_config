#!/bin/bash

SCRIPT_NAME="test_rseqc_reads_distribution_module"

./cleaner.sh

bpipe run test.groovy ../../data/*.bam
bpipe query > test.graph
RESULT=`diff expected.graph test.graph`
if [[ $RESULT > 0 ]]; then
    echo "Error for dependency graph"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
