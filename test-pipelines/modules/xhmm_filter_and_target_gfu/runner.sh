#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(DATA.filtered_centered.RD.txt.filtered_samples.txt DATA.filtered_centered.RD.txt.filtered_targets.txt DATA.filtered_centered.RD.txt)
run test.groovy DATA.RD.txt
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
