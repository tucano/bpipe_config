load "../../../modules/generate_truseq_intervals_gfu.groovy"

TRUSEQ = "/lustre1/genomes/hg19/annotation/TruSeq_10k.intervals"
REFERENCE_GENOME="/test/reference/pippo.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

Bpipe.run {
    chr(1..22,'X','Y') * [generate_truseq_intervals_gfu.using(pretend:true)]
}
