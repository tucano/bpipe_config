#!/bin/bash
source ../../testsupport.sh

./cleaner.sh
OUTPUTS=(testinput_one_broad_peaks.bed testinput_one_model.r testinput_one_peaks.bed testinput_one_peaks.narrowPeak testinput_one_peaks.xls testinput_one_summits.bed)
run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
