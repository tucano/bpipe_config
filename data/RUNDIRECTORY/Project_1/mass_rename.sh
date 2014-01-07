#!/bin/bash

for i in Sample_*
do
    cd $i
    for m in *.fastq.gz
    do
        mv $m ${i}_${m}
    done
    cd ..
done
