#!/bin/bash


source ../../testsupport.sh

./cleaner.sh

OUTPUTS=( testinput_one.geneBodyCoverage.pdf testinput_one.geneBodyCoverage.txt testinput_one.geneBodyCoverage_plot.r testinput_two.geneBodyCoverage.pdf testinput_two.geneBodyCoverage.txt testinput_two.geneBodyCoverage_plot.r)

run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success

