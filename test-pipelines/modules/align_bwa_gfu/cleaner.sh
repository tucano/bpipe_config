#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt test.graph .bpipe *.sai test.out *.bam doc
