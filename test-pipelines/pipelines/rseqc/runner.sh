#!/bin/bash
source ../../testsupport.sh
./cleaner.sh

OUTPUTS=(setstripe.log testinput_one.bam_stat.log testinput_one.reads_distribution.log testinput_one.idxstats.log testinput_one.geneBodyCoverage.pdf testinput_one.geneBodyCoverage.txt testinput_one.geneBodyCoverage_plot.r  testinput_one.GC.xls testinput_one.GC_plot.pdf testinput_one.GC_plot.r testinput_one.NVC.xls testinput_one.NVC_plot.pdf testinput_one.NVC_plot.r testinput_one.qual.boxplot.pdf testinput_one.qual.r)

config rseqc
runPipeLine rseqc.groovy testinput_one.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success

