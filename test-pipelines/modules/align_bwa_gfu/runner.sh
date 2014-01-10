#!/bin/bash

./cleaner.sh

# SINGLE FASTQ
bpipe run test.groovy testinput_R1_001.fastq > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
./cleaner.sh

# SINGLE FASTQ.GZ
bpipe run test_compressed.groovy testinput_R1_001.fastq.gz > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
./cleaner.sh

# PAIRED FASTQ.GZ
bpipe run test_compressed.groovy testinput_R*_001.fastq.gz > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
