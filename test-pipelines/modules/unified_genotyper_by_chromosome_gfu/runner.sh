#!/bin/bash

SCRIPT_NAME="test_unified_genotyper_by_chromosome_module"

./cleaner.sh

bpipe run test.groovy testinput_R1_001.bam chr1.intervals
# I expect 1 files
RES=`ls *.vcf | wc | awk {'print $1'}`
if [[ $RES != 1 ]]; then
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
