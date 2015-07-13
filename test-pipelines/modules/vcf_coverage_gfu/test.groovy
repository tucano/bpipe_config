load "../../../modules/vcf_coverage_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
    "%.vcf" * [vcf_coverage_gfu.using(pretend:true)]
}
