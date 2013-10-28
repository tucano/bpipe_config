load "../../../modules/align_soapsplice_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"

Bpipe.run {
    "_R*_%" * [align_soapsplice_gfu.using(test:true, paired:true)]
}
