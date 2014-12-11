about title: "DNA alignment with bwa mem (lane): IOS GFU 009"
// INFO_USAGE: bpipe-config pipe bwa_submit_lane (CWD)
// INFO_USAGE: bpipe-config pipe bwa_submit_lane Sample_* (runner.sh)

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.fastq.gz

REFERENCE_GENOME = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/bwa/BPIPE_REFERENCE_GENOME"
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_SAMPLE_INFO_HERE--


/*
 * PIPELINE NOTES:
 * We provide an alternatives to MarkDuplicates ro remove duplicates:
 * If you see this error with MarkDuplicates:
 * Exception in thread "main" net.sf.picard.PicardException: Value was put into PairInfoMap more than once.
 * you can switch to rmdup (samtools)
 * remove/comment the mark_duplicates_gfu stage and uncomment the rmdup_gfu stage to use it
 * Accepted values for compression: gz, fqz
 */
Bpipe.run {
    set_stripe_gfu +
    "L%_R*_%.fastq.gz" * [mem_bwa_gfu.using(
      pretend:false,
      paired:true,
      bwa_threads:2,
      sample_dir:false,
      use_shm:false,
      compression:"gz",
      phred_64: false
    )] + "*.bam" * [merge_bam_gfu.using(rename:false)] + verify_bam_gfu + mark_duplicates_gfu +
    // rmdup_gfu.using(paired:true) +
    bam_flagstat_gfu
}
