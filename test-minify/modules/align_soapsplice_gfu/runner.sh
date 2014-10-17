#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

# SINGLE
config
OUTPUTS=(sample1_L001_R1_001.bam sample1_L001_R1_002.bam sample1_L001_R1_003.bam sample1_L001_R1_004.bam sample1_L001_R2_001.bam sample1_L001_R2_002.bam sample1_L001_R2_003.bam sample1_L001_R2_004.bam)
run test_single.groovy ../../raw_data/sample1/*.gz *.header
checkTestOut
exists $OUTPUTS
./cleaner.sh

# PAIRED FASTQ
config
OUTPUTS=(sample1_L001_001.bam sample1_L001_002.bam sample1_L001_003.bam sample1_L001_004.bam)
run test_paired.groovy ../../raw_data/sample1/*.gz *.header
checkTestOut
exists $OUTPUTS
./cleaner.sh

cleanBpipeDir

success
