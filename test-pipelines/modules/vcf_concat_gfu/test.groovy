load "../../../modules/vcf_concat_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
    "*" * [vcf_concat_gfu.using(pretend:true)]
}
