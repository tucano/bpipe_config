#!/bin/bash

./cleaner.sh

bpipe run test.groovy testinput_one.reads.sam testinput_one.bam > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
