load "../../../modules/vcf_genotype_frequency_and_subsam_gfu.groovy"

HEALTY_EXOMES_DIR = "healty_exomes"

Bpipe.run {
    "%" * [vcf_genotype_frequency_and_subsam_gfu.using(pretend:true)]
}
