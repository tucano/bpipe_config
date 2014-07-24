load "../../../modules/tar_report_gfu.groovy"

CUSTOM_CSS      = "/templates/test.css"
README_TEMPLATE = "templates/README"
REPORT_DATA_DIR = "templates/report_data"

Bpipe.run {
    "*" * [tar_report_gfu.using(pretend:true)]
}
