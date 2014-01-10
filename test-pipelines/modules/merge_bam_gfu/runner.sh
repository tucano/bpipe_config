#!/bin/bash

./cleaner.sh
bpipe run test.groovy *.bam > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
./cleaner.sh

OUTPUTONE="TEST.merge.bam"
OUTPUTTWO="TEST.merge.bai"
bpipe run test_rename.groovy *.bam > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
if [[ ! -f $OUTPUTONE ]]; then
    echo "Error for $OUTPUTONE"
    exit 1
fi
if [[ ! -f $OUTPUTONE ]]; then
    echo "Error for $OUTPUTTWO"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
