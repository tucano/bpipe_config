about title: "Human variants annotation: IOS 005"
// INFO_USAGE: bpipe-config pipe exome_variants_annotation (CWD)
// INFO_USAGE: bpipe-config pipe exome_variants_annotation Sample_* (runner.sh)

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename input.vcf

DBSNP                  = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/dbSNP-142.chr.vcf"
DBNSFP                 = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/dbNSFP2.4.txt.gz"
INTERVALS_BED          = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions.bed"
SNPEFF_CONFIG          = "/lustre1/tools/etc/snpEff.config"
HEALTY_EXOMES_DIR      = "/lustre1/workspace/Stupka/HealthyExomes/"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"
SQL_GENES_TABLE        = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/BPIPE_REFERENCE_GENOME.refGene.sql"
PHI_SCORES             = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/Phi_scores_McArthur.csv"
VCF2XLS_ANNOTATION     = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/vcf2xls_annotations/G*"

//--BPIPE_ENVIRONMENT_HERE--

/*
 * PIPELINE NOTES:
 * INTERVALS:
 * check the README file in /lustre1/genomes/hg19/annotation/exomes_targets/README
 * for available options. The current Exome protocol is NEXTERA RAPID CAPTURE EXPANDED EXOME
 * HEALTY_EXOMES_DIR:
 * The pipeline is designed to remove the HealtyExomes samples from annotation
 * if you don't want to remove healty exomes remove the stage vcf_genotype_frequency_and_subsam_gfu
 *
 */
Bpipe.run {
    set_stripe_gfu + "%.vcf" * [vcf_genotype_frequency_and_subsam_gfu] +
    "%.filtered.vcf" * [ snpsift_annotate_gfu + snpsift_dbnsfp_gfu + bedtools_filter_intervals_gfu +
      snpeff_gfu + snpsift_filter_quality_gfu  + markvcf_gfu + snpsift_filter_impact_gfu +
      vcf_to_xls_gfu ]
}
