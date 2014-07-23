#!/bin/bash

source ../../testsupport.sh

./cleaner.sh

OUTPUTS=(report.Rmd report.html)
run test.groovy report_data/*
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(report.Rmd report.html)
run test_knitr.groovy report_data/*
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(report.Rmd report.html)
run test_nohealtyexomes.groovy report_data/*
checkTestOut
exists $OUTPUTS
./cleaner.sh

OUTPUTS=(report.Rmd report.html)
run test_noped.groovy report_data/*
checkTestOut
exists $OUTPUTS
./cleaner.sh

success
