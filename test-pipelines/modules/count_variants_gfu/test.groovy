load "../../../modules/count_variants_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
    "*.vcf" * [count_variants_gfu.using(pretend:true)]
}
