load "../../../modules/vcf_called_intervals_gfu.groovy"

INTERVALS_BED = "test"

Bpipe.run {
    "%.vcf" * [vcf_called_intervals_gfu.using(pretend:true)]
}
