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
	"_R*_%.fqz" *  [mem_bwa_gfu.using(
      pretend:false,
      paired:true,
      bwa_threads:2,
      sample_dir:false,
      use_shm: false,
      compression: "fqz",
      phred_64:true
  )]
}
