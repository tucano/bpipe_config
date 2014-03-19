about title: "FASTQC: quality control of fastq files - IOS XXX"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename <INPUT_DIRS>

// PROJECT VARS will be added by bpipe-config
// I don't wanna templates for a groovy file. Use simple regexp with PLACEHOLDERS
// Don't change my keywords in source pipeline file!
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
