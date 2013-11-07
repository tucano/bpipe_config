load "../../../modules/generate_truseq_intervals_gfu.groovy"

TRUSEQ = "/lustre1/genomes/hg19/annotation/TruSeq_10k.intervals"

Bpipe.run {
    chr(1..22,'X','Y') * [generate_truseq_intervals_gfu.using(test:true)]
}
