about title: "Quality Control of VCF files: IOS GFU XXX."

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -R softwareschedule $pipeline_filename *.vcf

REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

//--BPIPE_SAMPLE_INFO_HERE--


/*
 * PIPELINE NOTES:
 */
Bpipe.run {
  "%.vcf" * [vcf_coverage_gfu, vcf_called_intervals_gfu]
}
