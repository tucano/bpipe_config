about title: "DNA alignment with bwa aln+samse (single file): IOS GFU 009 [deprecated]"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.fastq.gz

// PROJECT VARS will be added by bpipe-config
// I don't wanna templates for a groovy file. Use simple regexp with PLACEHOLDERS
// Don't change my keywords in source!

REFERENCE_GENOME = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/bwa/BPIPE_REFERENCE_GENOME"
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

//--BPIPE_SAMPLE_INFO_HERE--


/*
 * PIPELINE NOTES:
 * We provide an alternatives to MarkDuplicates ro remove duplicates:
 * If you see this error with MarkDuplicates:
 * Exception in thread "main" net.sf.picard.PicardException: Value was put into PairInfoMap more than once.
 * you can switch to rmdup (samtools)
 * remove/comment the mark_duplicates_gfu stage and uncomment the rmdup_gfu stage to use it
 */
Bpipe.run {
    set_stripe_gfu +
    "%_R*" * [split_fastq_gfu.using(SPLIT_READS_SIZE:2000000,paired:false)] +
    "_%.fastq" * [align_bwa_gfu.using(paired:false,compression:"gz",BWAOPT_ALN:"",BWAOPT_SE:"")] +
    "*.bam" * [merge_bam_gfu.using(rename:true)] + verify_bam_gfu + mark_duplicates_gfu +
    // rmdup_gfu.using(paired:false) +
    bam_flagstat_gfu
}
