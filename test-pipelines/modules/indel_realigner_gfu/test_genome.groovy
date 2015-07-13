load "../../../modules/indel_realigner_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

REFERENCE_GENOME_FASTA = "TESTfa/hg19.fa"
INTERVALS              = "TESTannotation/TruSeq_10k.intervals"
DBSNP                  = "TESTannotation/dbSNP-137.chr.vcf"
HAPMAP                 = "TESTannotation/hapmap_3.3.hg19.sites.vcf.gz"
ONEKG_OMNI             = "TESTannotation/1000G_omni2.5.hg19.sites.vcf.gz"
ONETH_GENOMES          = "TESTannotation/1000G_phase1.snps.high_confidence.hg19.vcf"
MILLIS                 = "TESTannotation/Mills_and_1000G_gold_standard.indels.hg19.vcf"
PLATFORM               = "illumina"
CENTER                 = "CTGB"
ENVIRONMENT_FILE       = "gfu_environment.sh"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

Bpipe.run {
    "*" * [indel_realigner_gfu.using(pretend:true,target_intervals:false)]
}
