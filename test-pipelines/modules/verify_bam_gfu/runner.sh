#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

run test.groovy *.bam
checkTestOut
./cleaner.sh

run test_sampledir.groovy Sample_test_*
checkTestOut
./cleaner.sh

success
