about title: "FASTQC: quality control of fastq files (lane) - IOS XXX"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -R softwareschedule $pipeline_filename *.fastq.gz

PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_SAMPLE_INFO_HERE--


/*
 * PIPELINE NOTES:
 */
Bpipe.run {
    set_stripe_gfu + "*.fastq.gz" * [fastqc_lane_gfu]
}
