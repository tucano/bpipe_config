#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt .bpipe *.log test.out doc Sample_test_*_report/*.txt Sample_test_*_report/*.zip
