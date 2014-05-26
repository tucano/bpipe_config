#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.DATA.sample_interval_statistics testinput_one.DATA.sample_interval_summary testinput_one.DATA.sample_statistics testinput_one.DATA.sample_summary testinput_three.DATA.sample_interval_statistics testinput_three.DATA.sample_interval_summary testinput_three.DATA.sample_statistics testinput_three.DATA.sample_summary testinput_two.DATA.sample_interval_statistics testinput_two.DATA.sample_interval_summary testinput_two.DATA.sample_statistics testinput_two.DATA.sample_summary)

config depth_of_coverage
runPipeLine depth_of_coverage.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
