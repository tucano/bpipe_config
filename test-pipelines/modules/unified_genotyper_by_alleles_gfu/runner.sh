#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_R1_001.chr12.vcf testinput_R1_001.chr16.vcf testinput_R1_001.chr2.vcf testinput_R1_001.chr3.vcf testinput_R1_001.chr7.vcf testinput_R1_001.chrY.vcf testinput_R1_001.chr1.vcf testinput_R1_001.chr13.vcf testinput_R1_001.chr17.vcf testinput_R1_001.chr20.vcf testinput_R1_001.chr4.vcf  testinput_R1_001.chr8.vcf testinput_R1_001.chr10.vcf testinput_R1_001.chr14.vcf testinput_R1_001.chr18.vcf testinput_R1_001.chr21.vcf testinput_R1_001.chr5.vcf  testinput_R1_001.chr9.vcf testinput_R1_001.chr11.vcf testinput_R1_001.chr15.vcf testinput_R1_001.chr19.vcf testinput_R1_001.chr22.vcf testinput_R1_001.chr6.vcf testinput_R1_001.chrX.vcf input_bams.chr1.list)
run test.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
