load "../../../modules/rseqc_gene_coverage_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

BED12_ANNOTATION = "test"

Bpipe.run {
    "%" * [rseqc_gene_coverage_gfu.using(pretend:true)]
}
