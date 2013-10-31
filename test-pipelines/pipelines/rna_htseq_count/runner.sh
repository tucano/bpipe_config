#!/bin/bash

SCRIPT_NAME="test_rna_htseq_count_pipeline"

./cleaner.sh

BPIPE_LIB="../../../modules/" && bpipe run test.groovy ../../data/testinput_one.bam
# I expect 4 files
RES=`ls *.reads_sorted.* | wc | awk {'print $1'}`
if [[ $RES != 2 ]]; then
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
