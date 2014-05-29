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
	"_R*_%.fastq" *  [mem_bwa_gfu.using(
      pretend:true,
      paired:true,
      bwa_threads:2,
      sample_dir:false,
      use_shm: true,
      fqz_compressed: false,
      compressed:false,
      phred_64:false
  )]
}
