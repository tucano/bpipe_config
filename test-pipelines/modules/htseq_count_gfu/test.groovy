load "../../../modules/htseq_count_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

ANNOTATION_GFF_FILE    = "/lustre1/genomes/hg19/annotation/hg19.ensGene_withGeneName.gtf"
REFERENCE_GENOME="/test/reference/pippo.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"
ANNOTATION_GFF_FILE="test.gtf"

Bpipe.run {
    "*" * [htseq_count_gfu.using(pretend:true)]
}
