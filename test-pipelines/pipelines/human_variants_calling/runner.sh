#!/bin/bash

./cleaner.sh
bpipe-config pipe human_variants_calling
rm bpipe.config

BPIPE_LIB="../../../modules/" && bpipe run -p test=true PI_1A_name_human_variants_calling.groovy *.bam

# I expect 4 files
RES=`ls *.vcf | wc | awk {'print $1'}`
if [[ $RES != 29 ]]; then
    exit 1
fi

./cleaner.sh

echo "SUCCESS"
exit 0

