about title: "PIPELINE FOR SINGLE SAMPLE IN SAME DIR: IOS GFU #NUMBER."

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.merge.bam

REFERENCE_GENOME    = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/SOAPsplice/BPIPE_REFERENCE_GENOME.index"
PLATFORM            = "illumina"
CENTER              = "CTGB"
ENVIRONMENT_FILE    = "gfu_environment.sh"

//--BPIPE_SAMPLE_INFO_HERE--


/*
 * PIPELINE NOTES:
 */
Bpipe.run {
    
}
