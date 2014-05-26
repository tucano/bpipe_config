#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(DATA.RD.txt)
run test.groovy testinput_R1_001.DATA.sample_interval_summary
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
