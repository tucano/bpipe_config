load "../../../modules/indel_apply_recalibration_gfu.groovy"

Bpipe.run {
    "*" * [indel_apply_recalibration_gfu.using(test:true)]
}
