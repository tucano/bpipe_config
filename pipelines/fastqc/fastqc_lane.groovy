about title: "FASTQC: quality control of fastq files (lane) - IOS XXX"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.fastq.gz

// PROJECT VARS will be added by bpipe-config
// I don't wanna templates for a groovy file. Use simple regexp with PLACEHOLDERS
// Don't change my keywords in source pipeline file!
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--

/*
 * PIPELINE RUN
 */
Bpipe.run {
    set_stripe_gfu + "*.fastq.gz" * [fastqc_lane_gfu]
}
