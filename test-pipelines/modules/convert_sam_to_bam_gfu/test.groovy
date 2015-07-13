load "../../../modules/convert_sam_to_bam_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

PLATFORM="XXXX"
CENTER="ctgb"
ADD_OR_REPLACE_READ_GROUPS="AddOrReplaceReadGroups"

Bpipe.run {
    "*" * [convert_sam_to_bam_gfu.using(pretend:true)]
}
