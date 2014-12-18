about title: "FASTQC: quality control of fastq files - IOS XXX"
// INFO_USAGE: bpipe-config pipe fastqc_lane (CWD)
// INFO_USAGE: bpipe-config pipe fastqc_lane Sample_* (runner.sh)


// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.fastq.gz

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
