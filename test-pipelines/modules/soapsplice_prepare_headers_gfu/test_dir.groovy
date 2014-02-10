load "../../../modules/soapsplice_prepare_headers_gfu.groovy"

CENTER          = "CTGB"
PROJECTNAME     = "PI_1A_name"
REFERENCE       = "hg19"
EXPERIMENT_NAME = "experiment"
PLATFORM        = "illumina"

Bpipe.run {
    "%" * [soapsplice_prepare_headers_gfu.using(pretend:true,sample_dir:true)]
}
