load "../../../modules/snpsift_intervals_gfu.groovy"

TRUSEQ_REGIONS_BED = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/TruSeq-Exome-Targeted-Regions-BED-file.regions.bed"

Bpipe.run {
    "%" * [snpsift_intervals_gfu.using(pretend:true)]
}
