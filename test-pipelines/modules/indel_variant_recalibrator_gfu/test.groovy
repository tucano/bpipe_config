load "../../../modules/indel_variant_recalibrator_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

REFERENCE_GENOME_FASTA = "TESTfa/BPIPE_REFERENCE_GENOME.fa"
TRUSEQ                 = "TESTannotation/TruSeq_10k.intervals"
DBSNP                  = "TESTannotation/dbSNP-137.chr.vcf"
HAPMAP                 = "TESTannotation/hapmap_3.3.BPIPE_REFERENCE_GENOME.sites.vcf.gz"
ONEKG_OMNI             = "TESTannotation/1000G_omni2.5.BPIPE_REFERENCE_GENOME.sites.vcf.gz"
ONETH_GENOMES          = "TESTannotation/1000G_phase1.snps.high_confidence.BPIPE_REFERENCE_GENOME.vcf"
MILLIS                 = "TESTannotation/Mills_and_1000G_gold_standard.indels.BPIPE_REFERENCE_GENOME.vcf"
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
    "*" * [indel_variant_recalibrator_gfu.using(pretend:true)]
}
