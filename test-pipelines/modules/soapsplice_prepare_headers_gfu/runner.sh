#!/bin/bash

SCRIPT_NAME="test_rseqc_bam_stat_module"

./cleaner.sh

bpipe run test.groovy *.fastq.gz > test.out
grep 'Pipeline failed!' test.out 1>/dev/null 2>&1
if [[ $? == 0 ]]; then
    echo "FAIL"
    exit 1
fi
./cleaner.sh

echo "SUCCESS"
exit 0
