load "../../../modules/make_html_hsmetrics_gfu.groovy"

Bpipe.run {
    "%.tsv" * [make_html_hsmetrics_gfu.using(html_template:"../../../templates/metrics.html.template")]
}
