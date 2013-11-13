#!/bin/bash

SCRIPT_NAME="test_generate_truseq_intervals_module"

./cleaner.sh

bpipe run test.groovy ../../data/*.bam
# I expect 24 files
RES=`ls *.intervals | wc | awk {'print $1'}`
if [[ $RES != 24 ]]; then
    exit 1
fi

./cleaner.sh

echo "SUCCESS"
exit 0
