load "../../../modules/base_recalibrator_gfu.groovy"

REFERENCE_GENOME_FASTA = "/test/reference/pippo.fa"
INTERVALS              = "/test/nexterarapidcapture_expandedexome_targetedregions.intervals"
DBSNP                  = "/test/dbSNP-138.chr.vcf"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

Bpipe.run {
    "*" * [base_recalibrator_gfu.using(pretend:true,target_intervals:false)]
}
