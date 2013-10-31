about title: "FASTQC: quality control of fastq files in local directory (PROJECT): IOS XXX - UNDER CONSTRUCTION"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.fastq.gz

PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--

/*
 * This is the local version of the fastqc pipeline,
 * is used to add a fastqc quality control on your project data
 */
 Bpipe.run {
    set_stripe_gfu + fastqc_gfu.using(local: true)
 }
