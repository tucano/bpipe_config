#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

# SINGLE
OUTPUTS=(testinput_001.Aligned.out.sam testinput_001.Log.final.out testinput_001.Log.out testinput_001.Log.progress.out)
run test_single.groovy testinput_R1_001.fastq
checkTestOut
exists $OUTPUTS
./cleaner.sh

run test_single_compressed.groovy testinput_R1_001.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

# PAIRED FASTQ
OUTPUTS=(testinput_001.Aligned.out.sam testinput_001.Log.final.out testinput_001.Log.out testinput_001.Log.progress.out testinput_002.Aligned.out.sam testinput_002.Log.final.out testinput_002.Log.out testinput_002.Log.progress.out)
run test_paired.groovy *R*.fastq
checkTestOut
exists $OUTPUTS
./cleaner.sh

run test_paired_compressed.groovy *R*.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
