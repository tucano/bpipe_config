load "../../../modules/snpsift_filter_impact_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
    "%" * [snpsift_filter_impact_gfu.using(pretend:true)]
}
