about title: "Human variants calling for genomes: IOS 015"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

// PROJECT VARS will be added by bpipe-config
// I don't wanna templates for a groovy file. Use simple regexp with PLACEHOLDERS
// Don't change my keywords in source!

REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
DBSNP                  = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/dbSNP-138.chr.vcf"
HAPMAP                 = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/hapmap_3.3.BPIPE_REFERENCE_GENOME.sites.vcf.gz"
ONEKG_OMNI             = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/1000G_omni2.5.BPIPE_REFERENCE_GENOME.sites.vcf.gz"
ONETH_GENOMES          = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/1000G_phase1.snps.high_confidence.BPIPE_REFERENCE_GENOME.vcf"
MILLIS                 = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/Mills_and_1000G_gold_standard.indels.BPIPE_REFERENCE_GENOME.vcf"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--


// N.B: BY DEFAULT HEALTY EXOMES ARE NOT USED TO CALL VARIANTS! IF YOU WANT THEM, 
// SET healty_exomes:true and uncomment this line:
// HEALTY_EXOMES_DIR = "/lustre1/workspace/Stupka/HealthyExomes/"

// This pipeline RENAME the output vcf file from unified_genotyper to: all_samples.vcf
Bpipe.run {
    set_stripe_gfu +
    chr(1..22,'X','Y') * [ unified_genotyper_by_chromosome_gfu.using(rename:"all_samples",healty_exomes:false) ] + 
    vcf_concat_gfu +
    "%.vcf" * [
        snp_variant_recalibrator_gfu + snp_apply_recalibration_gfu,
        indel_variant_recalibrator_gfu + indel_apply_recalibration_gfu
    ] + vcf_concat_gfu.using(with_suffix: "vcf_merged_and_recalibrated") + snpsift_filter_duplicates_gfu
}
