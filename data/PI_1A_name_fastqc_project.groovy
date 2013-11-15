about title: "FASTQC: quality control of fastq files (project) - IOS XXX"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename Sample_*

// PROJECT VARS will be added by bpipe-config
// I don't wanna templates for a groovy file. Use simple regexp with PLACEHOLDERS
// Don't change my keywords in source pipeline file!
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

PROJECTNAME="PI_1A_name"
REFERENCE="hg19"
EXPERIMENT_NAME="D2A8DACXX_Sample_test_1"
FCID="D2A8DACXX"
LANE="3"
SAMPLEID="Sample_test_1"

/*
 * PIPELINE NOTES
 */
 Bpipe.run {
    set_stripe_gfu + "%" * [ fastqc_sample_gfu ]
 }
