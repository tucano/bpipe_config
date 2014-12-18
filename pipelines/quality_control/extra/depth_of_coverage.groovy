about title: "GATK: calculate Depth Of Coverage from BAM files - IOS XXX"
// INFO_USAGE: bpipe-config pipe depth_of_coverage (CWD)
// INFO_USAGE: bpipe-config pipe depth_of_coverage Sample_* (runner.sh)

// Usage line will be used to infer the correct bpipe command
// USAGE: bpipe run -r $pipeline_filename *.bam

PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"
INTERVALS              = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/truseq_and_nextera_merge_for_calibration.intervals"
REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"

//--BPIPE_ENVIRONMENT_HERE--


/*
 * PIPELINE NOTES:
 */
Bpipe.run {
    set_stripe_gfu + "%.bam" * [gatk_detpth_of_coverage_gfu]
}
