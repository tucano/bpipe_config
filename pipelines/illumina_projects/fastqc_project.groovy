about title: "FASTQC: quality control of fastq files - IOS XXX"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename <INPUT_DIRS>

PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--

/*
 * PIPELINE NOTES:
 */
Bpipe.run {
    set_stripe_gfu + "%" * [ make_report_dir_gfu + fastqc_sample_gfu.using(paired:true) ] +
    "*" * [project_report_fastqc_gfu]
}
