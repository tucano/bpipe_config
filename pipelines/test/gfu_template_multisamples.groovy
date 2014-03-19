about title: "PIPELINE FOR MULTIPLE SAMPLES IN SAME DIR: IOS GFU #NUMBER."

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.merge.bam

REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--


/*
 * PIPELINE NOTES:
 */
Bpipe.run {
    
}
