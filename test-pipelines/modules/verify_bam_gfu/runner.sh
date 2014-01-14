#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

run test.groovy *.bam
checkTestOut
./cleaner.sh

success
