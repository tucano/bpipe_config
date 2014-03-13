load "../../../modules/unzip_fastqc_gfu.groovy"

Bpipe.run {
    "%" * [unzip_fastqc_gfu.using(pretend:true)]
}
