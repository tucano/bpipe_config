#!/bin/bash


./cleaner.sh

bpipe run test_single.groovy *.sai *.fastq > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
RES=`ls *.bam | wc | awk {'print $1'}`
if [[ $RES != 4 ]]; then
    echo "Error for For multiple input fastq I expect 4 output files"
    exit 1
fi
./cleaner.sh

bpipe run test_paired.groovy *.sai *.fastq > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
RES=`ls *.bam | wc | awk {'print $1'}`
if [[ $RES != 2 ]]; then
    echo "Error for For multiple input fastq I expect 4 output files"
    exit 1
fi
./cleaner.sh


echo "SUCCESS"
exit 0
