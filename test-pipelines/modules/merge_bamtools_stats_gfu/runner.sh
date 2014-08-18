#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(all_samples.bamstats)
run test.groovy *.bamstats
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
