#!/bin/bash

./cleaner.sh

bpipe run test.groovy *.vcf > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
# I expect 23 files
RES=`ls *.csv | wc | awk {'print $1'}`
if [[ $RES != 24 ]]; then
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
