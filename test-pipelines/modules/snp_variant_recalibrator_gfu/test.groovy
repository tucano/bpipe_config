load "../../../modules/snp_variant_recalibrator_gfu.groovy"

Bpipe.run {
    "%" * [snp_variant_recalibrator_gfu.using(pretend:true)]
}
