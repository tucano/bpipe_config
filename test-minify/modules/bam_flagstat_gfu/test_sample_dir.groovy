load "../../../modules/bam_flagstat_gfu.groovy"

branches = [
    Sample_test_1:['Sample_test_1/sample1.bam'],
    Sample_test_2:['Sample_test_2/sample2.bam']
]

prepare = {
    branch.sample = branch.name
    forward input.bam
}

Bpipe.run {
    branches * [
        prepare + "*.bam" * [bam_flagstat_gfu.using(sample_dir:true)]
    ]
}
