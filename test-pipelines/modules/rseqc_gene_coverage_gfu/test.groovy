load "../../../modules/rseqc_gene_coverage_gfu.groovy"

Bpipe.run {
    "%" * [rseqc_gene_coverage_gfu.using(pretend:true)]
}
