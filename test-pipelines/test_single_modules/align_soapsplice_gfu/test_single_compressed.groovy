load "../../../modules/align_soapsplice_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"

Bpipe.run {
    "*" * [align_soapsplice_gfu.using(test:true, paired:false, compressed: true)]
}
