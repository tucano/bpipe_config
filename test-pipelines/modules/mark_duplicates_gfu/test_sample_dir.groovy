load "../../../modules/mark_duplicates_gfu.groovy"

branches = [
    Sample_test_1:['Sample_test_1/testinput_one.merge.bam'],
    Sample_test_2:['Sample_test_2/testinput_one.merge.bam']
]

prepare = {
    branch.sample = branch.name
    forward input.bam
}

Bpipe.run {
    branches * [
        prepare + "*.bam" * [mark_duplicates_gfu.using(pretend:true,sample_dir:true)]
    ]
}
