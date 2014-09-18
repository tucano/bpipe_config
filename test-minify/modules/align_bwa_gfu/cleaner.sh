#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt .bpipe *.sai test.out *.bam doc bpipe.config > /dev/null 2>&1
rm -rf .bpipe > /dev/null 2>&1
