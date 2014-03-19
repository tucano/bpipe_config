// MAKE HSMETRICS REPORT

@preserve
make_report_hsmetrics_gfu =
{
	var pretend : false

	doc title: "Make a report from a set of HS metrics",
        desc: "...",
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

	produce("HsMetrics_Report.tsv")
	{
		if (pretend)
		{
			exec "touch $output"
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
			new File("$output").write(report.toString())
		}
	}
}