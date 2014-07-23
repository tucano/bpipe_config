#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(bwa_version.txt)
run test.groovy
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
