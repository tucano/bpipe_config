#!/bin/bash

./cleaner.sh

bpipe run test.groovy testinput_R1_001.chr1.vcf input.tranches input.csv > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
