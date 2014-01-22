load "../../../modules/snpsift_dbnsfp_gfu.groovy"

DBNSFP = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/dbNSFP2.1.txt"

Bpipe.run {
    "%" * [snpsift_dbnsfp_gfu.using(pretend:true)]
}
