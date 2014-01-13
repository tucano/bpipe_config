#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput.merge.rmdup.bam)

run test.groovy testinput.merge.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
