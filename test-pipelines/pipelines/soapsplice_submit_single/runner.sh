#!/bin/bash

./cleaner.sh
bpipe-config pipe soapsplice_submit_single
rm bpipe.config

BPIPE_LIB="../../../modules/" && bpipe run -p test=true PI_1A_name_soapsplice_submit_single.groovy ../../data/*.fastq.gz
# I expect 4 files
RES=`ls *.merge.dedup.* | wc | awk {'print $1'}`
if [[ $RES != 4 ]]; then
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
