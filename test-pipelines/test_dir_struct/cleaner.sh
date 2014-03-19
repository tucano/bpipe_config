#!/bin/bash

for project in Tester_*
do
	rm -rf *.groovy $project/SampleSheet.csv $project/bpipe.config $project/.bpipe $project/*.groovy $project/commandlog.txt $project/runner.sh $project/doc
	for sample in ${project}/Sample_*
	do
		rm -rf $sample/gfu_environment.sh 
		rm -rf $sample/bpipe.config
		rm -rf $sample/.bpipe
		rm -rf $sample/commandlog.txt
		rm -rf $sample/doc
		rm -rf $sample/*.groovy
	done
done
rm -rf .bpipe bpipe.config SampleSheet.csv commandlog.txt runner.sh doc
