#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(healty_exomes.txt)
run test.groovy
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
