#!/bin/bash

SCRIPT_NAME="test_unified_genotyper_by_chromosome_module"

./cleaner.sh

bpipe run test.groovy ../../data/testinput_R1_001.bam ../../data/*.intervals
# I expect 23 files
RES=`ls *.vcf | wc | awk {'print $1'}`
if [[ $RES != 24 ]]; then
    echo "Error for For multiple input fastq I expect 4 output files"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
