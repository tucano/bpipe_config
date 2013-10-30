load "../../../modules/samtools_idxstats_gfu.groovy"

Bpipe.run {
    "%" * [samtools_idxstats_gfu.using(test:true)]
}
