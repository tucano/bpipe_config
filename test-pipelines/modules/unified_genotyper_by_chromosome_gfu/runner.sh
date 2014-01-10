#!/bin/bash

./cleaner.sh

bpipe run test.groovy testinput_R1_001.bam chr1.intervals > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi

# I expect 1 files
RES=`ls *.vcf | wc | awk {'print $1'}`
if [[ $RES != 1 ]]; then
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
