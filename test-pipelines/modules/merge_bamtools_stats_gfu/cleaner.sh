#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf commandlog.txt test.out .bpipe doc all_samples.bamstats.log all_samples.bamstats
