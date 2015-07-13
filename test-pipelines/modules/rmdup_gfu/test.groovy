load "../../../modules/rmdup_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
	"%" * [rmdup_gfu.using(pretend:true, paired: true)]
}
