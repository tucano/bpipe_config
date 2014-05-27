#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(DATA.vcf)
run test.groovy DATA.xcnv *.txt
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
