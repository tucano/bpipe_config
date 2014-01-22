load "../../../modules/snpsift_annotate_gfu.groovy"

Bpipe.run {
    "%" * [snpsift_annotate_gfu.using(pretend:true)]
}
