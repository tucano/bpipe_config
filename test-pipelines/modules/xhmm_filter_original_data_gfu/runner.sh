#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(DATA.same_filtered.RD.txt)
run test.groovy *.txt
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
