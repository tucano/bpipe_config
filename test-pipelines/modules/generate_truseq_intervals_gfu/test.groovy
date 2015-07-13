load "../../../modules/generate_truseq_intervals_gfu.groovy"
load "../../../modules/default_paths_gfu.groovy"

// INTERVALS CAPTURED BY THE EXOMES PROTOCOL
// check the README file in /lustre1/genomes/hg19/annotation/exomes_targets/README
// for available options. The current Exome protocol is NEXTERA RAPID CAPTURE EXPANDED EXOME
INTERVALS        = "/lustre1/genomes/BPIPE_REFERENCE_GENOME/annotation/exomes_targets/nexterarapidcapture_expandedexome_targetedregions.intervals"
REFERENCE_GENOME = "/test/reference/pippo.fa"
EXPERIMENT_NAME  = "test"
PLATFORM         = "test"
FCID             = "1"
EXPERIMENT_NAME  = "test1"
SAMPLEID         = "S1"
CENTER           = "GFU"
PROJECTNAME      = "TEST_1_TEST"

Bpipe.run {
    chr(1..22,'X','Y') * [generate_truseq_intervals_gfu.using(pretend:true)]
}
