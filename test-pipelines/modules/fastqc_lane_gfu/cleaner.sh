#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt .bpipe *.zip test.out doc
