load "../../../modules/vcf_concat_gfu.groovy"

Bpipe.run {
    "*" * [vcf_concat_gfu.using(pretend:true)]
}
