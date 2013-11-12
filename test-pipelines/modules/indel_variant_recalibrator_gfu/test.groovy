load "../../../modules/indel_variant_recalibrator_gfu.groovy"

Bpipe.run {
    "*" * [indel_variant_recalibrator_gfu.using(test:true)]
}
