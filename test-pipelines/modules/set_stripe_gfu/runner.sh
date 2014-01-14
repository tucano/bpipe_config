#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(setstripe.log)
run test.groovy *.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
