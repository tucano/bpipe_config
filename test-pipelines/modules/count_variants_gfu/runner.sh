#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(variants_count.txt)
run test.groovy *.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
