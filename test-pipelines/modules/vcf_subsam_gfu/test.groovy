load "../../../modules/vcf_subsam_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

HEALTY_EXOMES_DIR = "healty_exomes"

Bpipe.run {
    "%" * [vcf_subsam_gfu.using(pretend:true)]
}
