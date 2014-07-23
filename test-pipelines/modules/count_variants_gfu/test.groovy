load "../../../modules/count_variants_gfu.groovy"

Bpipe.run {
    "*.vcf" * [count_variants_gfu.using(pretend:true)]
}
