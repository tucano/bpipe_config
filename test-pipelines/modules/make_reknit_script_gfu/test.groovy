load "../../../modules/make_reknit_script_gfu.groovy"

REKNIT_TEMPLATE  = "../../../templates/reknit.groovy"

Bpipe.run {
  "report.Rmd" * [make_reknit_script_gfu]
}
