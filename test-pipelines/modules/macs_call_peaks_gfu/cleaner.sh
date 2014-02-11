#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt .bpipe *.bed *.xls *.r *.narrowPeak test.out doc
