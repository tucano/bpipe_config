load "../../../modules/bedtools_filter_intervals_gfu.groovy"

INTERVALS_BED = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions.bed"

Bpipe.run {
    "%" * [bedtools_filter_intervals_gfu.using(pretend:true)]
}
