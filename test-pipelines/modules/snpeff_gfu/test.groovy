load "../../../modules/snpeff_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

SNPEFF_CONFIG = "/lustre1/tools/etc/snpEff.config"

Bpipe.run {
    "%" * [snpeff_gfu.using(pretend:true)]
}
