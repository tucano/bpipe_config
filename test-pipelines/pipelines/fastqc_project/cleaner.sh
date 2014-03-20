#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt test.out .bpipe gfu_environment.sh *.groovy doc *.zip *_report Kajaste_80_LV* Sample_* SampleSheet.csv
