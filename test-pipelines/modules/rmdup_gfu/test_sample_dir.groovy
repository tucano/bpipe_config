load "../../../modules/rmdup_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

branches = [
    Sample_test_1:[
        'Sample_test_1/SampleSheet.csv',
        'Sample_test_1/testinput.merge.bam'
    ],
    Sample_test_2:[
        'Sample_test_2/SampleSheet.csv',
        'Sample_test_2/testinput.merge.bam'
    ]
]

prepare = {
    branch.sample = branch.name
    forward input.bam
}

Bpipe.run {
	branches * [
        prepare + "*.bam" * [rmdup_gfu.using(pretend:true,paired:true,sample_dir:true)]
    ]
}
