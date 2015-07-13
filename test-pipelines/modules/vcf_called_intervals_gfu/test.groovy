load "../../../modules/vcf_called_intervals_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

INTERVALS_BED = "test"

Bpipe.run {
    "%.vcf" * [vcf_called_intervals_gfu.using(pretend:true)]
}
