load "../../../modules/indel_realigner_gfu.groovy"

Bpipe.run {
    "*" * [indel_realigner_gfu.using(test:true)]
}
