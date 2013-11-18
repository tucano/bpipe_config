#!/bin/bash

./cleaner.sh
bpipe-config pipe htseq_count
rm bpipe.config

BPIPE_LIB="../../../modules/" && bpipe run -p test=true PI_1A_name_htseq_count.groovy ../../data/testinput_one.bam

# I expect 4 files
RES=`ls *.reads_sorted.* | wc | awk {'print $1'}`
if [[ $RES != 2 ]]; then
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
