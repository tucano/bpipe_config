#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt test.out .bpipe gfu_environment.sh *.groovy doc Tier2.xls healty_samples.txt
