load "../../../modules/markvcf_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

SQL_GENES_TABLE        = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/BPIPE_REFERENCE_GENOME.refGene.sql"
PHI_SCORES             = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/Phi_scores_McArthur.csv"

Bpipe.run {
    "%" * [markvcf_gfu.using(pretend:true)]
}
