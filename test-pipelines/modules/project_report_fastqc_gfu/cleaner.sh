#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt .bpipe *.log test.out doc *.html Kajaste_80_LV_fastqc_report
