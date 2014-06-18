#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Martelli_small_1_ATCACG_L006_R1_001_paired.fastq.gz Martelli_small_1_ATCACG_L006_R1_001_unpaired.fastq.gz Martelli_small_1_ATCACG_L006_R2_001_paired.fastq.gz Martelli_small_1_ATCACG_L006_R2_001_unpaired.fastq.gz)

config trim_reads_pair
runPipeLine trim_reads_pair.groovy *.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
