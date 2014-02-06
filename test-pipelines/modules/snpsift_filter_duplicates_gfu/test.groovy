load "../../../modules/snpsift_filter_duplicates_gfu.groovy"

Bpipe.run {
    "%" * [snpsift_filter_duplicates_gfu.using(pretend:true)]
}
