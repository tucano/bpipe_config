#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_001.DATA.sample_interval_statistics testinput_001.DATA.sample_interval_summary testinput_001.DATA.sample_statistics testinput_001.DATA.sample_summary)
run test.groovy testinput_001.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh


OUTPUTS=(all_samples.DATA.sample_interval_statistics all_samples.DATA.sample_interval_summary all_samples.DATA.sample_statistics all_samples.DATA.sample_summary input_bams.all.list)
run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
