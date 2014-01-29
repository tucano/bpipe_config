about title: "Human variants annotation: IOS 005"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

// PROJECT VARS will be added by bpipe-config
// I don't wanna templates for a groovy file. Use simple regexp with PLACEHOLDERS
// Don't change my keywords in source!

DBSNP                  = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/dbSNP-138.chr.vcf"
DBNSFP                 = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/dbNSFP2.1.txt"
TRUSEQ_REGIONS_BED     = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/TruSeq-Exome-Targeted-Regions-BED-file.regions.bed"
SNPEFF_CONFIG          = "/lustre1/tools/etc/snpEff.config"
HEALTY_EXOMES_DIR      = "/lustre1/workspace/Stupka/HealthyExomes/"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--

/*
 * PIPELINE NOTES:
 */
Bpipe.run {
    set_stripe_gfu + "%.vcf" * [
        vcf_subsam_gfu + snpsift_annotate_gfu + snpsift_dbnsfp_gfu + snpsift_intervals_gfu + snpeff_gfu
    ]
}
