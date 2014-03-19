#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(BAM/Sample_test_10_L001.merge.dedup.bam BAM/Sample_test_14_L001.merge.dedup.bam BAM/Sample_test_18_L001.merge.dedup.bam BAM/Sample_test_2_L001.merge.dedup.bam  BAM/Sample_test_6_L001.merge.dedup.bam BAM/Sample_test_11_L001.merge.dedup.bam BAM/Sample_test_15_L001.merge.dedup.bam BAM/Sample_test_19_L001.merge.dedup.bam BAM/Sample_test_3_L001.merge.dedup.bam  BAM/Sample_test_7_L001.merge.dedup.bam BAM/Sample_test_12_L001.merge.dedup.bam BAM/Sample_test_16_L001.merge.dedup.bam BAM/Sample_test_1_L001.merge.dedup.bam  BAM/Sample_test_4_L001.merge.dedup.bam  BAM/Sample_test_8_L001.merge.dedup.bam BAM/Sample_test_13_L001.merge.dedup.bam BAM/Sample_test_17_L001.merge.dedup.bam BAM/Sample_test_20_L001.merge.dedup.bam BAM/Sample_test_5_L001.merge.dedup.bam  BAM/Sample_test_9_L001.merge.dedup.bam)

config genome_align_project ../../../data/RUNDIRECTORY/Project_1/Sample_test_*
runPipeLine genome_align_project.groovy ../../../data/RUNDIRECTORY/Project_1/Sample_test_*

checkTestOut
exists $OUTPUTS
./cleaner.sh

success
