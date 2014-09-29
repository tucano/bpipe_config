load "../../../modules/mem_bwa_gfu.groovy"

REFERENCE_GENOME="../../genome/chr22.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

Bpipe.run {
	"%.fastq" * [mem_bwa_gfu.using(
      pretend:false,
      paired:false,
      bwa_threads:2,
      sample_dir:false,
      use_shm: false,
      compression: "",
      phred_64:false
  )]
}
