load "../../../modules/snpsift_filter_gfu.groovy"

Bpipe.run {
    "%" * [snpsift_filter_gfu.using(test:true)]
}
