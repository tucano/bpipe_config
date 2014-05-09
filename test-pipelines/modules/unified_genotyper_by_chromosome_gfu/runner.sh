#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_R1_001.chr12.vcf testinput_R1_001.chr16.vcf testinput_R1_001.chr2.vcf testinput_R1_001.chr3.vcf testinput_R1_001.chr7.vcf testinput_R1_001.chrY.vcf testinput_R1_001.chr1.vcf testinput_R1_001.chr13.vcf testinput_R1_001.chr17.vcf testinput_R1_001.chr20.vcf testinput_R1_001.chr4.vcf  testinput_R1_001.chr8.vcf testinput_R1_001.chr10.vcf testinput_R1_001.chr14.vcf testinput_R1_001.chr18.vcf testinput_R1_001.chr21.vcf testinput_R1_001.chr5.vcf  testinput_R1_001.chr9.vcf testinput_R1_001.chr11.vcf testinput_R1_001.chr15.vcf testinput_R1_001.chr19.vcf testinput_R1_001.chr22.vcf testinput_R1_001.chr6.vcf testinput_R1_001.chrX.vcf input_bams.chr1.list)
run test.groovy testinput_R1_001.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(all_samples.chr12.vcf all_samples.chr16.vcf all_samples.chr2.vcf all_samples.chr3.vcf all_samples.chr7.vcf all_samples.chrY.vcf all_samples.chr1.vcf all_samples.chr13.vcf all_samples.chr17.vcf all_samples.chr20.vcf all_samples.chr4.vcf  all_samples.chr8.vcf all_samples.chr10.vcf all_samples.chr14.vcf all_samples.chr18.vcf all_samples.chr21.vcf all_samples.chr5.vcf  all_samples.chr9.vcf all_samples.chr11.vcf all_samples.chr15.vcf all_samples.chr19.vcf all_samples.chr22.vcf all_samples.chr6.vcf all_samples.chrX.vcf  input_bams.chr1.list)
run test_rename.groovy testinput_R1_001.bam testinput_R1_002.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
