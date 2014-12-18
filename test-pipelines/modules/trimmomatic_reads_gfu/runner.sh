#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

# SINGLE
OUTPUTS=(testinput_R1_001_trimmed.fastq.gz)
run test.groovy testinput_R1_001.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

# SINGLE FQZ
OUTPUTS=(testinput_R1_001_trimmed.fastq)
run test_fqz.groovy testinput_R1_001.fqz
checkTestOut
exists $OUTPUTS
./cleaner.sh

# PAIR FASTQ
OUTPUTS=(testinput_R1_001_paired.fastq.gz testinput_R1_001_unpaired.fastq.gz testinput_R2_001_paired.fastq.gz testinput_R2_001_unpaired.fastq.gz)
run test_paired.groovy testinput_R1_001.fastq.gz testinput_R2_001.fastq.gz
checkTestOut
exists $OUTPUTS
./cleaner.sh

# PAIR FQZ
OUTPUTS=(testinput_R1_001_paired.fastq testinput_R1_001_unpaired.fastq testinput_R2_001_paired.fastq testinput_R2_001_unpaired.fastq)
run test_paired_fqz.groovy testinput_R1_001.fqz testinput_R2_001.fqz
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
