#!/bin/bash
source ../../testsupport.sh

./cleaner.sh
touch Sample_1/test_R1.zip Sample_1/test_R2.zip
run test.groovy Sample_1/*.zip
checkTestOut
./cleaner.sh

success
