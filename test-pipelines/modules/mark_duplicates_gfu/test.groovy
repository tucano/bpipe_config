load "../../../modules/mark_duplicates_gfu.groovy"

Bpipe.run {
    "*" * [mark_duplicates_gfu.using(test:true)]
}
