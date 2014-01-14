load "../../../modules/verify_bam_gfu.groovy"

Bpipe.run {
    "*" * [verify_bam_gfu.using(pretend:true)]
}
