about title: "BAM Recalibration for a multiple bams (genomes): IOS GFU 020"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

REFERENCE_GENOME       = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/bwa/BPIPE_REFERENCE_GENOME"
REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
DBSNP                  = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/dbSNP-138.chr.vcf"
HEALTY_EXOMES_DIR      = "/lustre1/workspace/Stupka/HealthyExomes/"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--


/*
 * PIPELINE NOTES:
 * HEALTY_EXOMES_DIR:
 * if base_recalibrator_gfu.using(healty_exomes:true)
 * the pipeline use the HealtyExomes in path to recalibrate the bam files
 */
Bpipe.run {
    "%.bam" * [ realiagner_target_creator_gfu.using(target_intervals:false) +
    			indel_realigner_gfu.using(target_intervals:false) ] +
    "*.bam" * [ base_recalibrator_gfu.using(healty_exomes:false,target_intervals:false) ] +
    "%.bam" * [ base_print_reads_gfu.using(target_intervals:false) ]
}
