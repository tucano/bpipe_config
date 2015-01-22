#!/bin/bash

bpipe cleanup -y > /dev/null 2>&1
rm -rf soapsplice_align_project.groovy bpipe.config doc gfu_environment.sh .bpipe *.merge.* commandlog.txt Sample_* BAM *.groovy test.out doc input.json
