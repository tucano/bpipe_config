#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_R1_001.chr1.snp.plot.R  testinput_R1_001.chr13.snp.plot.R testinput_R1_001.chr17.snp.plot.R testinput_R1_001.chr20.snp.plot.R testinput_R1_001.chr4.snp.plot.R  testinput_R1_001.chr8.snp.plot.R testinput_R1_001.chr10.snp.plot.R testinput_R1_001.chr14.snp.plot.R testinput_R1_001.chr18.snp.plot.R testinput_R1_001.chr21.snp.plot.R testinput_R1_001.chr5.snp.plot.R  testinput_R1_001.chr9.snp.plot.R testinput_R1_001.chr11.snp.plot.R testinput_R1_001.chr15.snp.plot.R testinput_R1_001.chr19.snp.plot.R testinput_R1_001.chr22.snp.plot.R testinput_R1_001.chr6.snp.plot.R  testinput_R1_001.chrX.snp.plot.R testinput_R1_001.chr12.snp.plot.R testinput_R1_001.chr16.snp.plot.R testinput_R1_001.chr2.snp.plot.R  testinput_R1_001.chr3.snp.plot.R  testinput_R1_001.chr7.snp.plot.R  testinput_R1_001.chrY.snp.plot.R testinput_R1_001.chr1.snp.recal.csv  testinput_R1_001.chr14.snp.recal.csv testinput_R1_001.chr19.snp.recal.csv testinput_R1_001.chr3.snp.recal.csv  testinput_R1_001.chr8.snp.recal.csv testinput_R1_001.chr10.snp.recal.csv testinput_R1_001.chr15.snp.recal.csv testinput_R1_001.chr2.snp.recal.csv  testinput_R1_001.chr4.snp.recal.csv  testinput_R1_001.chr9.snp.recal.csv testinput_R1_001.chr11.snp.recal.csv testinput_R1_001.chr16.snp.recal.csv testinput_R1_001.chr20.snp.recal.csv testinput_R1_001.chr5.snp.recal.csv  testinput_R1_001.chrX.snp.recal.csv testinput_R1_001.chr12.snp.recal.csv testinput_R1_001.chr17.snp.recal.csv testinput_R1_001.chr21.snp.recal.csv testinput_R1_001.chr6.snp.recal.csv  testinput_R1_001.chrY.snp.recal.csv testinput_R1_001.chr13.snp.recal.csv testinput_R1_001.chr18.snp.recal.csv testinput_R1_001.chr22.snp.recal.csv testinput_R1_001.chr7.snp.recal.csv testinput_R1_001.chr1.snp.tranches  testinput_R1_001.chr14.snp.tranches testinput_R1_001.chr19.snp.tranches testinput_R1_001.chr3.snp.tranches  testinput_R1_001.chr8.snp.tranches testinput_R1_001.chr10.snp.tranches testinput_R1_001.chr15.snp.tranches testinput_R1_001.chr2.snp.tranches  testinput_R1_001.chr4.snp.tranches  testinput_R1_001.chr9.snp.tranches testinput_R1_001.chr11.snp.tranches testinput_R1_001.chr16.snp.tranches testinput_R1_001.chr20.snp.tranches testinput_R1_001.chr5.snp.tranches  testinput_R1_001.chrX.snp.tranches testinput_R1_001.chr12.snp.tranches testinput_R1_001.chr17.snp.tranches testinput_R1_001.chr21.snp.tranches testinput_R1_001.chr6.snp.tranches  testinput_R1_001.chrY.snp.tranches testinput_R1_001.chr13.snp.tranches testinput_R1_001.chr18.snp.tranches testinput_R1_001.chr22.snp.tranches testinput_R1_001.chr7.snp.tranches)
./cleaner.sh


run test.groovy *.vcf
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
