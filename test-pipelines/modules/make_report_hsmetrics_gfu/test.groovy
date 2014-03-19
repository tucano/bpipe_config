load "../../../modules/make_report_hsmetrics_gfu.groovy"

Bpipe.run {
    "*.hsmetrics" * [make_report_hsmetrics_gfu]
}
