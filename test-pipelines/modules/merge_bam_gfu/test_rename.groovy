load "../../../modules/merge_bam_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

SAMPLEID="TEST"

Bpipe.run {
    "*" * [merge_bam_gfu.using(pretend:true,merge_mode:"sampleid")]
}
