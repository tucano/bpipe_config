load "../../../modules/set_stripe_gfu.groovy"

Bpipe.run {
    "*" * [set_stripe_gfu.using(pretend:true)]
}
