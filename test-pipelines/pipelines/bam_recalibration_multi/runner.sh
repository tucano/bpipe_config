#!/bin/bash

./cleaner.sh
bpipe-config pipe bam_recalibration_multi
rm bpipe.config

BPIPE_LIB="../../../modules/" && bpipe run -p test=true PI_1A_name_bam_recalibration_multi.groovy *.bam

# I expect a single grp file
RES=`ls *.grp | wc | awk {'print $1'}`
if [[ $RES != 1 ]]; then
    exit 1
fi

./cleaner.sh

echo "SUCCESS"
exit 0
