load "../../../modules/align_bwa_gfu.groovy"

REFERENCE_GENOME="/test/reference/pippo.fa"

Bpipe.run {
	"%" * [align_bwa_gfu.using(test:true, compressed: true)]
}
