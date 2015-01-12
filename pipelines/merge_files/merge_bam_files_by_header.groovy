about title: "merge bam files using header information (SM tag): IOS XXX"
// INFO_USAGE: bpipe-config pipe merge_bam_files_by_header (CWD)

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

//--BPIPE_ENVIRONMENT_HERE--

/*
 * PIPELINE NOTES:
 */
Bpipe.run {
  "*.bam" * [merge_bam_by_header_prepare_gfu + merge_bam_by_header_json_gfu + merge_bam_by_headers_gfu]
}
