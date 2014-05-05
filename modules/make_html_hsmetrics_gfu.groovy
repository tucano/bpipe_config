// MAKE HTML HSMETRICS REPORT
import groovy.text.SimpleTemplateEngine

make_html_hsmetrics_gfu =
{
  var html_template : "/lustre1/tools/libexec/bpipeconfig/templates/metrics.html.template"
  var pretend       : false
  var output_dir    : ""

  doc title: "Make a report from a set of HS metrics",
      desc: "...",
      constraints: "...",
      author: "davide.rambaldi@gmail.com"

  if (output_dir != "") output.dir = output_dir

  produce("HsMetrics_Report.html")
  {
    if (pretend)
    {
      if (output_dir != "")
      {
        exec "touch ${output_dir}/HsMetrics_Report.html"
      }
      else
      {
        exec "touch HsMetrics_Report.html"
      }
    }
    else
    {
      // write an html page
      def output_filename_html = ""
      if (output_dir != "")
      {
        output_filename_html = "${output_dir}/HsMetrics_Report.html"
      }
      else
      {
        output_filename_html = "HsMetrics_Report.html"
      }
      def report = new File(input).readLines()
      String[] headers = report.remove(0).split("\t")

      def engine = new SimpleTemplateEngine()
      def headers_html = new StringBuffer()
      def samples_html = new StringBuffer()

      headers_html << "<tr>"
      headers.each { head ->
        headers_html << "<th>$head</th>"
      }
      headers_html << "</tr>"

      report.each { line ->
        samples_html << "<tr>"
        line.split("\t").each { info ->
          samples_html << "<td>$info</td>"
        }
        samples_html << "</tr>"
      }

      def template = new File(html_template).text

      def binding = [
        "headers_html" : headers_html,
        "samples_html" : samples_html
      ]

      def html = engine.createTemplate(template).make(binding).toString()
      new File(output_filename_html).write(html)
    }
  }
}
