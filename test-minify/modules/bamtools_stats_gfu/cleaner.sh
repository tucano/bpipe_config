#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt test.out doc *.bamstats bpipe.config > /dev/null 2>&1
