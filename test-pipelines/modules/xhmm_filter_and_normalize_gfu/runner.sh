#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(DATA.PCA_normalized.txt)
run test.groovy DATA.filtered_centered.RD.txt
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
