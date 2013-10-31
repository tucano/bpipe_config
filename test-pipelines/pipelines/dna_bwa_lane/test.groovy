about title: "DNA alignment with bwa (lane): IOS GFU 009"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.fastq.gz

// PROJECT VARS will be added by bpipe-config
// I don't wanna templates for a groovy file. Use simple regexp with PLACEHOLDERS
// Don't change my keywords in source pipeline file!

REFERENCE_GENOME = "/lustre1/genomes/hg19/bwa/hg19"
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

PROJECTNAME="bpipeconfig_1_test"
REFERENCE="hg19"
EXPERIMENT_NAME="D2A8DACXX_B1"
FCID="D2A8DACXX"
LANE="3"
SAMPLEID="B1"


/*
 * PIPELINE NOTES:
 * Use -q INT to trim quality of reads (example -q 30)
 * Use -I for base64 Illumina quality
 */
Bpipe.run {
    set_stripe_gfu.using(test: true) + "_R*_%.fastq.gz" * [align_bwa_gfu.using(BWAOPT_ALN: "-q 30", test: true, paired: true)] +
    "*.bam" * [merge_bam_gfu.using(rename: true, test: true)] +
    verify_bam_gfu.using(test: true) + mark_duplicates_gfu.using(test: true) + bam_flagstat_gfu.using(test: true)
}
