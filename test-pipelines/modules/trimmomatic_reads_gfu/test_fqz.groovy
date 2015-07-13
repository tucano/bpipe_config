load "../../../modules/trimmomatic_reads_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
    "%" * [trimmomatic_reads_gfu.using(pretend:true,paired:false,compression:"fqz")]
}
