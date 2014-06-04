load "../../../modules/unified_genotyper_by_alleles_gfu.groovy"

REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
DBSNP                  = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/dbSNP-138.chr.vcf"
HAPMAP                 = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/hapmap_3.3.BPIPE_REFERENCE_GENOME.sites.vcf.gz"
ONEKG_OMNI             = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/1000G_omni2.5.BPIPE_REFERENCE_GENOME.sites.vcf.gz"
ONETH_GENOMES          = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/1000G_phase1.snps.high_confidence.BPIPE_REFERENCE_GENOME.vcf"
MILLIS                 = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/Mills_and_1000G_gold_standard.indels.BPIPE_REFERENCE_GENOME.vcf"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"
ALLELES                = "/lustre2/scratch/Briganti/29_Prostatic_Cancer/1000G_snps_indels_in_panel_with_TSI_frequencies.vcf"

Bpipe.run {
    chr(1..22,'X','Y') * [unified_genotyper_by_alleles_gfu.using(pretend:true)]
}
