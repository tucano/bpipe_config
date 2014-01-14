#!/bin/bash
source ../../testsupport.sh

./cleaner.sh
mkdir -p Sample_1 Sample_2
run test.groovy Sample_*
checkTestOut
./cleaner.sh

success
