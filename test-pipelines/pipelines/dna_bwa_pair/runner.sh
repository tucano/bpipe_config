#!/bin/bash

SCRIPT_NAME="test_dna_bwa_lane_pipeline"

./cleaner.sh

BPIPE_LIB="../../../modules/" && bpipe run test.groovy ../../data/*.fastq.gz
# I expect 4 files
RES=`ls *.merge.dedup.* | wc | awk {'print $1'}`
if [[ $RES != 4 ]]; then
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
