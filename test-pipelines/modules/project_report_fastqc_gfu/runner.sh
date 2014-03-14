#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Kajaste_80_LV_fastqc_report/index.html)
run test.groovy Sample_*_report/*_fastqc_data.txt
checkTestOut
exists $OUTPUTS
./cleaner.sh

success