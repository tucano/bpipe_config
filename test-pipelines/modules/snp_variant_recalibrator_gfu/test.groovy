load "../../../modules/snp_variant_recalibrator_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

REFERENCE_GENOME_FASTA = "test"
HAPMAP = "test"
ONETH_GENOMES = "test"
ONEKG_OMNI = "test"
DBSNP = "test"

Bpipe.run {
    "%" * [snp_variant_recalibrator_gfu.using(pretend:true)]
}
