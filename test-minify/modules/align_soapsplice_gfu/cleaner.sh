#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt test.graph .bpipe *.bam *.sam test.out doc bpipe.config > /dev/null 2>&1
rm -rf .bpipe > /dev/null 2>&1