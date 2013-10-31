about title: "RNA paired ends alignment with soapsplice: IOS GFU 009."

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.fastq.gz

// PROJECT VARS will be added by bpipe-config
// I don't wanna templates for a groovy file. Use simple regexp with PLACEHOLDERS
// Don't change my keywords in source pipeline file!

REFERENCE_GENOME = "/lustre1/genomes/hg19/SOAPsplice/hg19.index"
REFERENCE_FAIDX  = "/lustre1/genomes/hg19/fa/hg19.fa.fai"
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

PROJECTNAME="PI_9_name"
REFERENCE="hg19"
EXPERIMENT_NAME="D2A8DACXX_B1"
FCID="D2A8DACXX"
LANE="3"
SAMPLEID="B1"

/*
 * PIPELINE NOTES:
 * Default soapsplice options: SSPLICEOPT_ALN : "-f 2 -q 1 -j 0"
 * Override sopasplice options with: align_soapsplice_gfu.using(paired: true, SSPLICEOPT_ALN : "<options>")
 */
 Bpipe.run {
    set_stripe_gfu.using(test:true) + "%" * [split_fastq_gfu.using(SPLIT_READS_SIZE: 2000000, paired: false, test : true)] +
    "%.fastq" * [soapsplice_prepare_headers_gfu.using(test:true) + align_soapsplice_gfu.using(paired: false, test: true, compressed: false)] +
    verify_bam_gfu.using(test:true) + merge_bam_gfu.using(rename:true, test:true) + merge_junc_gfu.using(test:true) +
    mark_duplicates_gfu.using(test:true) + bam_flagstat_gfu.using(test:true)
 }
