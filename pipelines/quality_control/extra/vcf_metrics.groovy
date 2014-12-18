about title: "Quality Control of VCF files: IOS GFU XXX."
// INFO_USAGE: bpipe-config pipe vcf_metrics (CWD)
// INFO_USAGE: bpipe-config pipe vcf_metrics Sample_* (runner.sh)

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.vcf

REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"
INTERVALS_BED          = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions.bed"

//--BPIPE_SAMPLE_INFO_HERE--


/*
 * PIPELINE NOTES:
 */
Bpipe.run {
  "%.vcf" * [vcf_coverage_gfu, vcf_called_intervals_gfu]
}
