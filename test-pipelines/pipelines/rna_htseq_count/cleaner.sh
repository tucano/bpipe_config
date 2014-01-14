#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt test.out .bpipe *reads_sorted.bam *.bai *.metrics *.log doc *.junc *.sam *.txt *.groovy gfu_environment.sh
