about title: "RNA single file alignment with STAR: IOS CTGB XXX."
// INFO_USAGE: bpipe-config pipe star_submit_single (CWD)
// INFO_USAGE: bpipe-config pipe star_submit_single Sample_* (runner.sh)

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.fastq.gz

REFERENCE_GENOME = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/STAR"
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_SAMPLE_INFO_HERE--

/*
 * PIPELINE NOTES:
 */

Bpipe.run {
  set_stripe_gfu + "L%_R*_%.fastq.gz" * [split_fastq_gfu.using(SPLIT_READS_SIZE:2000000,paired:true)] +
  "_%.fastq" * [align_star_gfu.using(paired:true, compression:"") + convert_sam_to_bam_gfu + verify_bam_gfu] +
  "*.bam" * [merge_bam_gfu.using(merge_mode:"samplesheet")] + verify_bam_gfu + bam_flagstat_gfu
}
