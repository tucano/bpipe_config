#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(Martelli_small_1_ATCACG_L006_R1_001_trimmed.fastq.gz Martelli_small_1_ATCACG_L006_R2_001_trimmed.fastq.gz)

config trim_reads_single
runPipeLine trim_reads_single.groovy *.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
