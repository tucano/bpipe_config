load "../../../modules/soapsplice_prepare_headers_gfu.groovy"

Bpipe.run {
    "%" * [soapsplice_prepare_headers_gfu.using(pretend:true)]
}
