#!/bin/bash

for project in Tester_*
do
	rm -rf $project/SampleSheet.csv $project/bpipe.config $project/.bpipe $project/*.groovy $project/commandlog.txt $project/runner.sh $project/doc
done
rm -rf .bpipe bpipe.config SampleSheet.csv commandlog.txt runner.sh doc