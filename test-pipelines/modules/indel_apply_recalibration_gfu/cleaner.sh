#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt test.out .bpipe *.pdf *.txt *.r *.xls *.indel_recalibrated.vcf doc
