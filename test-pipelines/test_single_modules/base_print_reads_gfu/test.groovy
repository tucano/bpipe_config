load "../../../modules/base_print_reads_gfu.groovy"

Bpipe.run {
    "*" * [base_print_reads_gfu.using(test:true)]
}
