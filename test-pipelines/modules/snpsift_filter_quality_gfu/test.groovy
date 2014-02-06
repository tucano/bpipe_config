load "../../../modules/snpsift_filter_quality_gfu.groovy"

Bpipe.run {
    "%" * [snpsift_filter_quality_gfu.using(pretend:true)]
}
