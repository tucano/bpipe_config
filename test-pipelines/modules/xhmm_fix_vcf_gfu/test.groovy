load "../../../modules/xhmm_fix_vcf_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"
REFERENCE_GENOME_FASTA = "/lustre1/genomes/hg19/fa/hg19.fa"

Bpipe.run {
    "*" * [xhmm_fix_vcf_gfu.using(pretend:true)]
}
