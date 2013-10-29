#!/bin/bash

SCRIPT_NAME="test_verify_bam_module"

./cleaner.sh

bpipe run test.groovy ../../data/*.bam

if [[ $? > 0 ]]; then
    echo "Error for $SCRIPT_NAME"
    exit 1
fi

./cleaner.sh

echo "SUCCESS"
exit 0
