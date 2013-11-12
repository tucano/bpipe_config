#!/bin/bash

./cleaner.sh

bpipe run test.groovy ../../data/*.vcf
# I expect 23 files
RES=`ls *.csv | wc | awk {'print $1'}`
if [[ $RES != 24 ]]; then
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
