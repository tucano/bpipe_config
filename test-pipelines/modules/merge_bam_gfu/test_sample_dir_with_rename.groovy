load "../../../modules/merge_bam_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

branches = [
    Sample_test_1:['Sample_test_1/testinput_001.bam','Sample_test_1/testinput_002.bam'],
    Sample_test_2:['Sample_test_2/testinput_001.bam','Sample_test_2/testinput_002.bam']
]

prepare = {
    branch.sample = branch.name
    forward input.bam
}

Bpipe.run {
    branches * [
        prepare + "*.bam" * [merge_bam_gfu.using(pretend:true,merge_mode:"samplesheet",sample_dir:true)]
    ]
}
