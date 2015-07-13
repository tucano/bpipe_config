load "../../../modules/mark_duplicates_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
    "*" * [mark_duplicates_gfu.using(pretend:true)]
}
