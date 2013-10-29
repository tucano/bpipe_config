load "../../../modules/fastqc_gfu.groovy"

Bpipe.run {
    "*" * [fastqc_gfu.using(test:true)]
}
