#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_one.NVC.xls testinput_one.NVC_plot.pdf testinput_one.NVC_plot.r testinput_two.NVC.xls testinput_two.NVC_plot.pdf testinput_two.NVC_plot.r)

run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
