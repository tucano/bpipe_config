about title: "BAM Recalibration for a single bam (genomes): IOS CTGB 020"
// INFO_USAGE: bpipe-config pipe genome_bam_recalibration_single (CWD)
// INFO_USAGE: bpipe-config pipe genome_bam_recalibration_single Sample_* (runner.sh)

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

REFERENCE_GENOME       = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/bwa/BPIPE_REFERENCE_GENOME"
REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
DBSNP                  = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/dbSNP-138.chr.vcf"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

//--BPIPE_SAMPLE_INFO_HERE--


/*
 * PIPELINE NOTES:
 */
Bpipe.run {
    "%.bam" * [ realiagner_target_creator_gfu.using(target_intervals:false) +
    			indel_realigner_gfu.using(target_intervals:false) +
    			base_recalibrator_gfu.using(target_intervals:false) +
    			base_print_reads_gfu.using(target_intervals:false) ]
}
