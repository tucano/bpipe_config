load "../../../modules/set_stripe_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
    "*" * [set_stripe_gfu.using(pretend:true)]
}
