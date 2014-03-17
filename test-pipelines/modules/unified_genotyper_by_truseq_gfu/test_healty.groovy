load "../../../modules/unified_genotyper_by_truseq_gfu.groovy"

REFERENCE_GENOME_FASTA = "/lustre1/genomes/hg19/fa/hg19.fa"
DBSNP                  = "/lustre1/genomes/hg19/annotation/dbSNP-138.chr.vcf"
HAPMAP                 = "/lustre1/genomes/hg19/annotation/hapmap_3.3.hg19.sites.vcf.gz"
ONEKG_OMNI             = "/lustre1/genomes/hg19/annotation/1000G_omni2.5.hg19.sites.vcf.gz"
ONETH_GENOMES          = "/lustre1/genomes/hg19/annotation/1000G_phase1.snps.high_confidence.hg19.vcf"
MILLIS                 = "/lustre1/genomes/hg19/annotation/Mills_and_1000G_gold_standard.indels.hg19.vcf"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"
HEALTY_EXOMES_DIR      = "healty_exomes"

Bpipe.run {
    chr(1) * [unified_genotyper_by_truseq_gfu.using(pretend:true,rename:"all_samples",healty_exomes:true)]
}
