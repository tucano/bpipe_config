#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt .bpipe test.out *.bam doc Sample_test_*
