load "../../../modules/mem_bwa_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
FCID="1"
EXPERIMENT_NAME="test1"
SAMPLEID="S1"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

Bpipe.run {
	"%.fastq.gz" * [mem_bwa_gfu.using(
      pretend:true,
      paired:false,
      bwa_threads:2,
      sample_dir:false,
      use_shm: false,
      fqz_compressed: false,
      compressed:true
  )]
}
