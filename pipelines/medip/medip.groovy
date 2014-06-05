about title: "meDIP pipeline: IOS GFU XXX."

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.merge.dedup.bam

PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

GSIZE            = "hs"
/* GSIZE FOR MACS2 callpeak
 * Effective genome size. It can be 1.0e+9 or 1000000000
 *  or shortcuts:'hs' for human (2.7e9), 'mm' for mouse
 * (1.87e9), 'ce' for C. elegans (9e7) and 'dm' for
 * fruitfly (1.2e8), Default:hs
 */


//--BPIPE_ENVIRONMENT_HERE--


/*
 * PIPELINE NOTES:
 */
Bpipe.run {
    set_stripe_gfu + "%.bam" * [ macs_call_peaks_gfu.using(format:"BAM",keep_dup:"1",broad:true) ]
}
