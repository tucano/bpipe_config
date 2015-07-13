load "../../../modules/healty_exomes_info_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

HEALTY_EXOMES_DIR = "test"

Bpipe.run {
    healty_exomes_info_gfu.using(pretend:true)
}
