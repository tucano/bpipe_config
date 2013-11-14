load "../../../modules/fastqc_sample_gfu.groovy"

Bpipe.run {
    "%" * [ fastqc_sample_gfu.using(test:true) ]
}
