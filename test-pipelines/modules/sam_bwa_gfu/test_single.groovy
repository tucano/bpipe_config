load "../../../modules/sam_bwa_gfu.groovy"

REFERENCE_GENOME = "/lustre1/genomes/hg19/bwa/hg19"
PLATFORM         = "illumina"
CENTER           = "CTGB"
ENVIRONMENT_FILE = "gfu_environment.sh"

PROJECTNAME="bpipeconfig_1_test"
REFERENCE="hg19"
EXPERIMENT_NAME="D2A8DACXX_B1"
FCID="D2A8DACXX"
LANE="3"
SAMPLEID="B1"

Bpipe.run {
    "%.*" * [sam_bwa_gfu.using(test:true, paired: false, compressed: false)]
}
