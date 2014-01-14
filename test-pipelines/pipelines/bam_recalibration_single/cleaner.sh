#!/bin/bash

bpipe cleanup -y 1>/dev/null 2>&1
rm -rf commandlog.txt test.out .bpipe gfu_environment.sh *.groovy doc *.indel_realigned.recalibrated.bam
