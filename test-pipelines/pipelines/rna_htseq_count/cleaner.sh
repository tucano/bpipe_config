#!/bin/bash

bpipe cleanup -y
rm -rf commandlog.txt test.graph .bpipe *.bam *.bai *.metrics *.log doc *.junc *.sam *.txt *.groovy gfu_environment.sh
