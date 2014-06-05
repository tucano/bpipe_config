about title: "Human variants calling for alleles: IOS XXX"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
DBSNP                  = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/dbSNP-138.chr.vcf"
HAPMAP                 = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/hapmap_3.3.BPIPE_REFERENCE_GENOME.sites.vcf.gz"
ONEKG_OMNI             = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/1000G_omni2.5.BPIPE_REFERENCE_GENOME.sites.vcf.gz"
ONETH_GENOMES          = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/1000G_phase1.snps.high_confidence.BPIPE_REFERENCE_GENOME.vcf"
MILLIS                 = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/Mills_and_1000G_gold_standard.indels.BPIPE_REFERENCE_GENOME.vcf"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"
ALLELES                = "/path/to/alleles/file"

//--BPIPE_ENVIRONMENT_HERE--

/*
 * PIPELINE NOTES:
 * This pipeline RENAME the output vcf file from unified_genotyper to: all_samples.vcf
 *
 * HEALTY_EXOMES_DIR:
 * BY DEFAULT HEALTY EXOMES ARE NOT USED TO CALL VARIANTS FOR GENOME VARIANTS CALLING!
 * IF YOU WANT THEM, SET healty_exomes:true and uncomment the HEALTY_EXOMES_DIR line:
 */
Bpipe.run {
    set_stripe_gfu +
    chr(1..22,'X','Y') * [ unified_genotyper_by_alleles_gfu.using(rename:"all_samples") ] +
    "*.vcf" * [vcf_concat_gfu + vcf_coverage_gfu.using(output_dir:"doc")]
}
