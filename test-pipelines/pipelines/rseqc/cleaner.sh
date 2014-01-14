#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt test.out .bpipe  *.log doc *.junc *.sam *.txt *.groovy gfu_environment.sh *.r *.pdf *.xls
