load "../../../modules/vcf_to_xls_gfu.groovy"

VCF2XLS_ANNOTATION     = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/vcf2xls_annotations/G*"

Bpipe.run {
    "%" * [vcf_to_xls_gfu.using(pretend:true)]
}
