// MAKE HSMETRICS REPORT

@preserve
make_report_hsmetrics_gfu =
{
	var pretend       : false
	var output_dir    : ""

	doc title: "Make a report from a set of HS metrics",
        desc: "...",
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

  if (output_dir != "") output.dir = output_dir

	produce("HsMetrics_Report.tsv")
	{
		if (pretend)
		{
			if (output_dir != "")
			{
				exec "touch ${output_dir}/HsMetrics_Report.tsv; touch ${output_dir}/HsMetrics_Report.html"
			}
			else
			{
				exec "touch HsMetrics_Report.tsv; touch HsMetrics_Report.html"
			}

		}
		else
		{
			// use groovy to extract info from metrics
			def samples = [:]
			def headers = new File("$input1").readLines()[6].split("\t")
			inputs.each { file_name ->
				def file = new File(file_name)
				def sample = file.getName().replaceAll(/\..*/,"")
				def hsmetrics = file.readLines()[7]
				samples[sample] = hsmetrics.split("\t")
			}

			// write a metrics file with sample name as first column
			def report = new StringBuffer()
			report << ["SAMPLE_NAME", headers].flatten().join("\t") << "\n"

			samples.each { sample_name, hsdata ->
				report << "$sample_name" << "\t" << hsdata.join("\t") << "\n"
			}

			def output_filename_csv = ""
			if (output_dir != "")
			{
				output_filename_csv = "${output_dir}/HsMetrics_Report.tsv"
			}
			else
			{
				output_filename_csv = "HsMetrics_Report.tsv"
			}
			new File("$output_filename_csv").write(report.toString())
		}
	}
}
