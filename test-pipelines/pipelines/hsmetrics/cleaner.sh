#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt test.out .bpipe gfu_environment.sh *.groovy doc HsMetrics_Report.tsv HsMetrics_Report.html
