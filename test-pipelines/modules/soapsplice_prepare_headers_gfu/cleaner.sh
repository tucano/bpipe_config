#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt test.out .bpipe *.header doc Sample_1/*.header
