about title: "BAM Recalibration for a single bam: IOS GFU 020"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

// PROJECT VARS will be added by bpipe-config
// I don't wanna templates for a groovy file. Use simple regexp with PLACEHOLDERS
// Don't change my keywords in source!

REFERENCE_GENOME       = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/bwa/BPIPE_REFERENCE_GENOME"
REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"

// INTERVALS CAPTURED BY THE EXOMES PROTOCOL
// check the README file in /lustre1/genomes/hg19/annotation/exomes_targets/README
// for available options. The current Exome protocol is NEXTERA RAPID CAPTURE EXPANDED EXOME
INTERVALS        = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions.intervals"
DBSNP            = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/dbSNP-138.chr.vcf"

PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--


Bpipe.run {
    "%.bam" * [ realiagner_target_creator_gfu + indel_realigner_gfu + base_recalibrator_gfu + base_print_reads_gfu ]
}
