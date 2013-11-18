#!/bin/bash

./cleaner.sh
bpipe-config pipe rseqc
rm bpipe.config

BPIPE_LIB="../../../modules/" && bpipe run -p test=test PI_1A_name_rseqc.groovy ../../data/testinput_one.bam

# I expect 14 files
RES=`ls testinput_one.*  | wc | awk {'print $1'}`
if [[ $RES != 14 ]]; then
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
