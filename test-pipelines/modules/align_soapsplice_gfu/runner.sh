#!/bin/bash

SCRIPT_NAME="test_align_soaplice_gfu_module"

./cleaner.sh

# SINGLE FASTQ
bpipe run test_single.groovy testinput_R1_001.fastq testinput_R1_001.header > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
./cleaner.sh

# SINGLE FASTQ.GZ
bpipe run test_single_compressed.groovy testinput_R1_001.fastq.gz testinput_R1_001.header > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
./cleaner.sh

# MULTI FASTQ
bpipe run test_multi.groovy *.fastq *.header > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
./cleaner.sh

# MULTI FASTQ.GZ
bpipe run test_multi_compressed.groovy *.fastq.gz *.header > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
./cleaner.sh

# PAIRED FASTQ
bpipe run test_paired.groovy *.fastq *.header > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
./cleaner.sh

# PAIRED FASTQ.GZ
bpipe run test_paired_compressed.groovy *.fastq.gz *.header
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0

