load "../../../modules/base_recalibrator_gfu.groovy"

Bpipe.run {
    "*" * [base_recalibrator_gfu.using(test:true)]
}
