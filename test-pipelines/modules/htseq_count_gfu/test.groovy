load "../../../modules/htseq_count_gfu.groovy"

EXPERIMENT_NAME        = "TEST"
ANNOTATION_GFF_FILE    = "/lustre1/genomes/hg19/annotation/hg19.ensGene_withGeneName.gtf"

Bpipe.run {
    "*" * [htseq_count_gfu.using(test:true)]
}
