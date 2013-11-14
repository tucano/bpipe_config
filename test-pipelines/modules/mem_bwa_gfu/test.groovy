load "../../../modules/mem_bwa_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"

Bpipe.run {
	"%.fastq.gz" * [mem_bwa_gfu.using(test:true, paired: false)]
}
