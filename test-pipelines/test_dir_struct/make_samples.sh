#!/bin/bash

for project in $*
do
	echo "SAMPLES FOR PROJECT: $project"
	cd $project
	for i in 1 2 3 4 5 6 7 8
	do
		SAMPLE_DIR="Sample_${i}"
		SAMPLE_INFO="FCID=XXXXXX,Lane=1,SampleID=Sample_${i},SampleRef=hg19,Index=TTAGGC,Description=description,Control=N,Recipe=MeDIP,Operator=FG,SampleProject=${project}"
		echo "SAMPLE DIR: $SAMPLE_DIR"
		echo "SAMPLE INFO: $SAMPLE_INFO"
		mkdir $SAMPLE_DIR
		cd $SAMPLE_DIR
		bpipe-config sheet $SAMPLE_INFO
		cd ..
	done
	cd ..
done