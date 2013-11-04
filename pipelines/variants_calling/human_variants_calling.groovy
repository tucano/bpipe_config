about title: "Human variants calling: IOS 005 - UNDER CONSTRUCTION"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

// PROJECT VARS will be added by bpipe-config
// I don't wanna templates for a groovy file. Use simple regexp with PLACEHOLDERS
// Don't change my keywords in source!

GATK                   = "java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"
VCFCONCAT              = "export PERL5LIB=/lustre1/tools/libexec/vcftools_0.1.9/perl/ && vcf-concat"
REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
TRUSEQ                 = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/TruSeq_10k.intervals"
DBSNP                  = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/dbSNP-137.chr.vcf"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--

/*
 * PIPELINE NOTES:
 */
Bpipe.run {
    chr(1..22,'X','Y') * [ generate_truseq_intervals_gfu + unified_genotyper_by_chromosome_gfu ] + vcf_concat_gfu
}
