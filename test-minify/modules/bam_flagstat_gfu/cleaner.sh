#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt *.log test.out doc Sample_test_*/*.log bpipe.config > /dev/null 2>&1
