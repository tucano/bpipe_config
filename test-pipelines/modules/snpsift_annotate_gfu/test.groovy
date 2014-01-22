load "../../../modules/snpsift_annotate_gfu.groovy"


DBSNP = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/dbSNP-138.chr.vcf"

Bpipe.run {
    "%" * [snpsift_annotate_gfu.using(pretend:true)]
}
