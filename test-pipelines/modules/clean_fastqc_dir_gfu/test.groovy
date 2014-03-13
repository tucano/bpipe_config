load "../../../modules/clean_fastqc_dir_gfu.groovy"

Bpipe.run {
    "*" * [clean_fastqc_dir_gfu]
}
