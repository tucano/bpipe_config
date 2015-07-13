load "../../../modules/snpsift_intervals_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

INTERVALS_BED = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions.bed"

Bpipe.run {
    "%" * [snpsift_intervals_gfu.using(pretend:true)]
}
