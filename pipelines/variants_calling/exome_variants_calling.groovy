about title: "Human variants calling for exome: IOS 005"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

INTERVALS              = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions.intervals"
HEALTY_EXOMES_DIR      = "/lustre1/workspace/Stupka/HealthyExomes/"
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


/*
 * PIPELINE NOTES:
 * This pipeline RENAME the output vcf file from unified_genotyper to: all_samples.vcf
 *
 * INTERVALS:
 * check the README file in /lustre1/genomes/hg19/annotation/exomes_targets/README
 * for available options. The current Exome protocol is NEXTERA RAPID CAPTURE EXPANDED EXOME
 *
 * HEALTY_EXOMES_DIR:
 * if unified_genotyper_by_truseq_gfu.using(healty_exomes:true)
 * the pipeline use the HealtyExomes in path to call variants
 */
Bpipe.run {
    set_stripe_gfu + chr(1..22,'X','Y') * 
    [ generate_truseq_intervals_gfu + unified_genotyper_by_truseq_gfu.using(rename:"all_samples",healty_exomes:true) ] + 
    vcf_concat_gfu +
    "%.vcf" * [
        snp_variant_recalibrator_gfu + snp_apply_recalibration_gfu,
        indel_variant_recalibrator_gfu + indel_apply_recalibration_gfu
    ] + vcf_concat_gfu.using(with_suffix:"vcf_merged_and_recalibrated") + snpsift_filter_duplicates_gfu
}
