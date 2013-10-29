#!/bin/bash

SCRIPT_NAME="test_dna_bwa_lane_pipeline"

# INPUTBAM="../../data/testinput_one.bam"
# OUTPUTONE="testinput_one.grp"

./cleaner.sh

bpipe run test.groovy ../../data/*.fastq.gz
# I expect 4 files
RES=`ls *.merge.dedup.* | wc | awk {'print $1'}`
if [[ $RES != 4 ]]; then
    echo "Error for For multiple input fastq I expect 4 output files"
    exit 1
fi

./cleaner.sh

echo "SUCCESS"
exit 0
