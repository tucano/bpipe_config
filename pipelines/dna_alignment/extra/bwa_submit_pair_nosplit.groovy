about title: "DNA paired ends alignment with bwa mem without splitting input fastq: IOS CTGB 009"
// INFO_USAGE: bpipe-config pipe bwa_submit_pair_nosplit (CWD)
// INFO_USAGE: bpipe-config pipe bwa_submit_pair_nosplit Sample_* (runner.sh)

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.fastq.gz

REFERENCE_GENOME = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/bwa/BPIPE_REFERENCE_GENOME"
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_SAMPLE_INFO_HERE--

/*
 * PIPELINE NOTES:
 */
Bpipe.run {
    set_stripe_gfu + "%_R*" *[mem_bwa_gfu.using(
      pretend:false,
      paired:true,
      bwa_threads:2,
      sample_dir:false,
      use_shm:false,
      compression:"gz",
      phred_64: false
    )] + verify_bam_gfu + mark_duplicates_gfu + bam_flagstat_gfu
}
