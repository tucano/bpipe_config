#!/bin/bash

SCRIPT_NAME="test_base_recalibrator_module"
OUTPUTONE="testinput.merge.bam"
OUTPUTTWO="testinput.merge.bai"

./cleaner.sh

bpipe run test.groovy ../../data/*.bam
if [[ ! -f $OUTPUTONE ]]; then
    echo "Error for $OUTPUTONE"
    exit 1
fi
if [[ ! -f $OUTPUTONE ]]; then
    echo "Error for $OUTPUTTWO"
    exit 1
fi
./cleaner.sh

OUTPUTONE="TEST.merge.bam"
OUTPUTTWO="TEST.merge.bai"
bpipe run test_rename.groovy ../../data/*.bam
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
