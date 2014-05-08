load "../../../modules/vcf_called_intervals_gfu.groovy"

Bpipe.run {
    "%.vcf" * [vcf_called_intervals_gfu.using(pretend:true)]
}
