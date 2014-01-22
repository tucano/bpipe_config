#!/bin/bash
source ../../testsupport.sh

./cleaner.sh

OUTPUTS=()

config human_variants_annotation
runPipeLine human_variants_calling.groovy *.bam
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
