about title: "BAM Recalibration for a single bam (exomes): IOS GFU 020"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

REFERENCE_GENOME       = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/bwa/BPIPE_REFERENCE_GENOME"
REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
INTERVALS              = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions.intervals"
DBSNP                  = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/dbSNP-138.chr.vcf"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

//--BPIPE_SAMPLE_INFO_HERE--

/*
 * PIPELINE NOTES:
 * INTERVALS:
 * check the README file in /lustre1/genomes/hg19/annotation/exomes_targets/README
 * for available options. The current Exome protocol is NEXTERA RAPID CAPTURE EXPANDED EXOME
 */
Bpipe.run {
    "%.bam" * [ realiagner_target_creator_gfu.using(target_intervals:true) + 
    			indel_realigner_gfu.using(target_intervals:true) + 
    			base_recalibrator_gfu.using(target_intervals:true) + 
    			base_print_reads_gfu.using(target_intervals:true) ]
}
