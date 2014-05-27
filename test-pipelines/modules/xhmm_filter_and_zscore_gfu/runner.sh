#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(DATA.PCA_normalized.filtered.sample_zscores.RD.txt.filtered_samples.txt DATA.PCA_normalized.filtered.sample_zscores.RD.txt.filtered_targets.txt DATA.PCA_normalized.filtered.sample_zscores.RD.txt)
run test.groovy DATA.PCA_normalized.txt
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
