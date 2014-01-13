load "../../../modules/merge_bam_gfu.groovy"

SAMPLEID="TEST"

Bpipe.run {
    "*" * [merge_bam_gfu.using(pretend:true,rename:true)]
}
