load "../../../modules/mem_bwa_gfu.groovy"
load "../../../modules/sample_dir_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"
EXPERIMENT_NAME="test"
PLATFORM="test"
CENTER="GFU"
PROJECTNAME="TEST_1_TEST"

Bpipe.run {
    "%" * [
        sample_dir_gfu +
        "*_R*_%.fastq.gz" * [mem_bwa_gfu.using(
              pretend:true,
              paired:true,
              bwa_threads:2,
              sample_dir:true,
              use_shm: false,
              fqz_compressed: false,
              compressed:true
            )]
    ]
}
