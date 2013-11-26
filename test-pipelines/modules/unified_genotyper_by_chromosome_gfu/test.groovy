load "../../../modules/unified_genotyper_by_chromosome_gfu.groovy"

Bpipe.run {
    chr(1) * [unified_genotyper_by_chromosome_gfu.using(test:true)]
}
