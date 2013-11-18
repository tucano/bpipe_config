#!/bin/bash

./cleaner.sh
bpipe-config pipe rna_seq_lane
rm bpipe.config

BPIPE_LIB="../../../modules/" && bpipe run -p test=true PI_1A_name_rna_seq_lane.groovy ../../data/*.fastq.gz
# I expect 23 files
RES=`ls *.merge.dedup.* | wc | awk {'print $1'}`
if [[ $RES != 23 ]]; then
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
