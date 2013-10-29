load "../../../modules/realiagner_target_creator_gfu.groovy"

Bpipe.run {
    "*" * [realiagner_target_creator_gfu.using(test:true)]
}
