#!/bin/bash

bpipe cleanup -y >/dev/null 2>&1
rm -rf commandlog.txt test.out .bpipe doc Sample_1/fastqc_report_R1 Sample_1/fastqc_report_R2 Sample_2/fastqc_report_R1 Sample_2/fastqc_report_R2
