load "../../../modules/rmdup_gfu.groovy"

Bpipe.run {
	"%" * [rmdup_gfu.using(test:true, paired: true)]
}
