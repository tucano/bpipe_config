#!/bin/bash

SCRIPT_NAME="test_bam_recalibration_pipeline"
INPUTBAM="../../data/testinput_one.bam"

./cleaner.sh

BPIPE_LIB="../../../modules/" && bpipe run test.groovy $INPUTBAM
# I expect 14 files
RES=`ls testinput_one.*  | wc | awk {'print $1'}`
if [[ $RES != 14 ]]; then
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
