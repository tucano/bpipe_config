#!/bin/bash
BPIPECONFIG=""
for i in Sample_*
do
    cd $i
    export BPIPE_CONFIG_HOME=../../../../build/stage/bpipeconfig && ../../../../build/stage/bpipeconfig/bin/bpipe-config sheet FCID=D2A8DACXX,Lane=3,SampleID=$i,SampleRef=hg19,Index=TTAGGC,Description=description,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=PI_1A_name
    cd ..
done
