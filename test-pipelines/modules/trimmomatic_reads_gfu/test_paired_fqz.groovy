load "../../../modules/trimmomatic_reads_gfu.groovy"

Bpipe.run {
    "%_R*" * [trimmomatic_reads_gfu.using(pretend:true, paired:true, compression:"fqz")]
}
