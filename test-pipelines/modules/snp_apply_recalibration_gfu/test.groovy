load "../../../modules/snp_apply_recalibration_gfu.groovy"

Bpipe.run {
    "*" * [snp_apply_recalibration_gfu.using(test:true)]
}
