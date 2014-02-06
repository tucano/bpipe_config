load "../../../modules/snpsift_filter_impact_gfu.groovy"

Bpipe.run {
    "%" * [snpsift_filter_impact_gfu.using(pretend:true)]
}
