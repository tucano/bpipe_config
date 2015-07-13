load "../../../modules/bwa_software_version_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

Bpipe.run {
    bwa_software_version_gfu.using(pretend:true)
}
