load "../../../modules/calculate_hsmetrics_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

REFERENCE_GENOME_FASTA = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/fa/BPIPE_REFERENCE_GENOME.fa"
BAITS                  = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_probes.interval_list"
TARGETS                = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions.interval_list"
TARGETS_EXPANDED       = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions_slope200.interval_list"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"

Bpipe.run {
    "%.bam" * [calculate_hsmetrics_gfu.using(pretend:true)]
}
