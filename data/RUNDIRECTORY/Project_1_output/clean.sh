#!/bin/bash
bpipe cleanup -y > /dev/null
rm -rf bwa_submit_project.groovy bpipe.config doc gfu_environment.sh .bpipe *.merge.* commandlog.txt Sample_* BAM .bpipe input.json
