#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(DATA.aux_xcnv DATA.xcnv)
run test.groovy *.txt
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
