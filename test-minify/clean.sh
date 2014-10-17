#!/bin/bash

cd modules/

TESTS=`find . -maxdepth 1 -type d | grep "[A-Za-z]"`
for t in $TESTS;
do
        cd "$t/";
        ./cleaner.sh 1>/dev/null 2>&1
        rm -rf .bpipe
        cd ..
done

cd ..

cd pipelines/

TESTS=`find . -maxdepth 1 -type d | grep "[A-Za-z]"`
for t in $TESTS;
do
        cd "$t/";
        ./cleaner.sh 1>/dev/null 2>&1
        rm -rf .bpipe
        cd ..
done

cd ..
