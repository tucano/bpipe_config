load "../../../modules/mem_bwa_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"

Bpipe.run {
	"R*_%" * [mem_bwa_gfu.using(test:true, paired: true)]
}
