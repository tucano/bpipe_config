load "../../../modules/gatk_detpth_of_coverage_gfu.groovy"

REFERENCE_GENOME_FASTA = "/lustre1/genomes/hg19/fa/hg19.fa"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"
INTERVALS              = "/lustre1/genomes/hg19/annotation/exomes_targets/truseq_and_nextera_merge_for_calibration.intervals"

Bpipe.run {
    "*.bam" * [gatk_detpth_of_coverage_gfu.using(pretend:true)]
}
