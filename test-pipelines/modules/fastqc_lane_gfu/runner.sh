#!/bin/bash

./cleaner.sh

bpipe run test.groovy ../../data/*.fastq.gz
# I expect 2 files
RES=`ls *.zip | wc | awk {'print $1'}`
if [[ $RES != 2 ]]; then
    exit 1
fi
./cleaner.sh

bpipe run test_single.groovy ../../data/*.fastq.gz
# I expect 1 files
RES=`ls *.zip | wc | awk {'print $1'}`
if [[ $RES != 1 ]]; then
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
