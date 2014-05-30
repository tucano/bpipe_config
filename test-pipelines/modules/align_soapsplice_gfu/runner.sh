#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

# SINGLE
OUTPUTS=(testinput_R1_001.bam)
run test_single.groovy testinput_R1_001.fastq testinput_R1_001.header
checkTestOut
exists $OUTPUTS
./cleaner.sh

run test_single_compressed.groovy testinput_R1_001.fastq.gz testinput_R1_001.header
checkTestOut
exists $OUTPUTS
./cleaner.sh

# MULTI FASTQ NOT PAIRED
OUTPUTS=(testinput_R1_001.bam testinput_R1_002.bam testinput_R2_001.bam testinput_R2_002.bam)
run test_multi.groovy *.fastq *.header
checkTestOut
exists $OUTPUTS
./cleaner.sh

run test_multi_compressed.groovy *.fastq.gz *.header
checkTestOut
exists $OUTPUTS
./cleaner.sh

# PAIRED FASTQ
OUTPUTS=(testinput_001.bam testinput_002.bam)
run test_paired.groovy *.fastq *.header
checkTestOut
exists $OUTPUTS
./cleaner.sh

run test_paired_compressed.groovy *.fastq.gz *.header
checkTestOut
exists $OUTPUTS
./cleaner.sh

# SHM
OUTPUTS=(testinput_001.bam testinput_002.bam)
run test_paired_shm.groovy *.fastq *.header
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
