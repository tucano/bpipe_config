load "../../../modules/soapsplice_prepare_headers_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

CENTER          = "CTGB"
PROJECTNAME     = "PI_1A_name"
REFERENCE       = "hg19"
EXPERIMENT_NAME = "experiment"
FCID            = "1111111"
LANE            = "L001"
SAMPLEID        = "1"
PLATFORM        = "illumina"
REFERENCE_FAIDX = "test"

Bpipe.run {
    "%" * [soapsplice_prepare_headers_gfu.using(pretend:true)]
}
