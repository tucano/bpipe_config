#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.GC.xls testinput_one.GC_plot.pdf testinput_one.GC_plot.r testinput_two.GC.xls testinput_two.GC_plot.pdf testinput_two.GC_plot.r)

run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
