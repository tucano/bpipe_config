#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(testinput_R1_001.chr1.vcf input_bams.chr1.list)
run test.groovy testinput_R1_001.bam chr1.intervals
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(all_samples.chr1.vcf input_bams.chr1.list)
run test_rename.groovy testinput_R1_001.bam chr1.intervals
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(all_samples.chr1.vcf input_bams.chr1.list)
run test_healty.groovy testinput_R1_001.bam chr1.intervals
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(testinput_R1_001.chr1.vcf input_bams.chr1.list)
run test_nogroups.groovy testinput_R1_001.bam chr1.intervals
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
