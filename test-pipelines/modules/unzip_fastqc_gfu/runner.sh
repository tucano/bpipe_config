#!/bin/bash
source ../../testsupport.sh

./cleaner.sh
run test.groovy Sample_*/*.zip
checkTestOut
./cleaner.sh

success
