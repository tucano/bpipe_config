#!/bin/bash

# 0. clean
bpipe cleanup -y

# 1. clean output and logs
rm -rf .bpipe read* *.sai *.bam *merge* *dedup* commandlog.txt run.log run.err *.junc doc bpipe.config *.groovy gfu_environment.sh

