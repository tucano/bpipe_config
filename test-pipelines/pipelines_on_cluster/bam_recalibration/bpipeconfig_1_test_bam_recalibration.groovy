about title: "BAM Recalibration: IOS GFU 020"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

// PROJECT VARS will be added by bpipe-config
// I don't wanna templates for a groovy file. Use simple regexp with PLACEHOLDERS
// Don't change my keywords in source!

REFERENCE_GENOME       = "/lustre1/genomes/hg19/bwa/hg19"
REFERENCE_GENOME_FASTA = "/lustre1/genomes/hg19/fa/hg19.fa"
TRUSEQ                 = "/lustre1/genomes/hg19/annotation/TruSeq_10k.intervals"
DBSNP                  = "/lustre1/genomes/hg19/annotation/dbSNP-137.chr.vcf"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

PROJECTNAME="bpipeconfig_1_test"
REFERENCE="hg19"
EXPERIMENT_NAME="D2A8DACXX_B1"
FCID="D2A8DACXX"
LANE="3"
SAMPLEID="B1"


/*
 * PIPELINE NOTES:
 */
Bpipe.run {
    realiagner_target_creator_gfu.using(ref_genome_fasta: REFERENCE_GENOME_FASTA, truseq: TRUSEQ, dbsnp: DBSNP) + 
    indel_realigner_gfu.using(ref_genome_fasta: REFERENCE_GENOME_FASTA, truseq : TRUSEQ, dbsnp : DBSNP) +
    base_recalibrator_gfu.using(ref_genome_fasta: REFERENCE_GENOME_FASTA, truseq : TRUSEQ, dbsnp : DBSNP) +
    base_print_reads_gfu.using(ref_genome_fasta: REFERENCE_GENOME_FASTA, truseq : TRUSEQ)
}