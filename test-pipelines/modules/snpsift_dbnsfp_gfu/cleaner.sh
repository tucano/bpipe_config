#!/bin/bash

bpipe cleanup -y >/dev/null 2>&1
rm -rf commandlog.txt test.out .bpipe doc *.dbnsfp.*
