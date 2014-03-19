about title: "DNA paired ends alignment with bwa: IOS GFU 009"

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.fastq.gz

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
    set_stripe_gfu + "%_R*" * [split_fastq_gfu.using(SPLIT_READS_SIZE:2000000,paired:true)] +
    "read*_%.fastq" * [mem_bwa_gfu.using(paired:true,compressed:false,BWAOPT_MEM:"")] +
    "*.bam" * [merge_bam_gfu.using(rename:true)] + verify_bam_gfu + bam_flagstat_gfu +
    mark_duplicates_gfu +
    // rmdup_gfu.using(paired:true) +
    bam_flagstat_gfu
}
