#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(DATA.RD_PCA.PC_LOADINGS.txt DATA.RD_PCA.PC.txt DATA.RD_PCA.PC_SD.txt)
run test.groovy DATA.filtered_centered.RD.txt
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
