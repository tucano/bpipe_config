// MODULE MERGE BAMTOOLS STATS

@preserve
merge_bamtools_stats_gfu =
{
	var pretend       : false
	var output_dir    : ""
	var with_name     : "all_samples.bamstats.log"

	doc title: "Make a report from a set of HS metrics",
		desc: "...",
		constraints: "...",
		author: "davide.rambaldi@gmail.com"

	if (output_dir != "")
	{
		output.dir = output_dir
		with_name = "${output_dir}/${with_name}"
	}

	produce("$with_name")
	{
		if (pretend)
		{
			exec "touch ${with_name};"
		}
		else
		{
			// use groovy to extract info from metrics
			def samples = [:]
			inputs.each { file_name ->
				def file   = new File(file_name)
				def sample = file.getName().replaceAll(/\..*/,"")
				def bamstats = [:]
				file.readLines()[5..18].collect() { fileh ->
					def data = fileh.split(/:/)
					if (data[1] ==~ /.*\(.*\).*/)
					{
						def nested_data = data[1].split(/\t/)
						bamstats[data[0]] = nested_data.collect() { it.trim().replaceAll(/[\(\)%]/,"") }
					}
					else
					{
						bamstats[data[0]] = [data[1].trim()]
					}
				}
				samples[sample] = bamstats
			}

			// HEADERS
			def res_table = new StringBuffer()
			res_table << "SAMPLE_NAME\tTotal reads\tMapped reads\tMapped reads(%)\tForward strand\tForward strand (%)\tReverse strand\tReverse strand (%)\tFailed QC\tFailed QC (%)\tDuplicates\tDuplicates (%)\t"
			res_table << "Paired-end reads\tPaired-end reads (%)\tProper-pairs\tProper-pairs (%)\tBoth pairs mapped\tBoth pairs mapped (%)\tRead1\tRead2\tSingletons\tSingletons (%)\t"
			res_table << "Average insert size (absolute value)\tMedian insert size (absolute value)\n"
			samples.each { key, value ->
				res_table << "$key\t"
				res_table << value["Total reads"][0] << "\t"
				res_table << value["Mapped reads"][0] << "\t" << value["Mapped reads"][1] << "\t"
				res_table << value["Forward strand"][0] << "\t" << value["Forward strand"][1] << "\t"
				res_table << value["Reverse strand"][0] << "\t" << value["Reverse strand"][1] << "\t"
				res_table << value["Failed QC"][0] << "\t" << value["Failed QC"][1] << "\t"
				res_table << value["Duplicates"][0] << "\t" << value["Duplicates"][1] << "\t"
				res_table << value["Paired-end reads"][0] << "\t" << value["Paired-end reads"][1] << "\t"
				res_table << value["'Proper-pairs'"][0] << "\t" << value["'Proper-pairs'"][1] << "\t"
				res_table << value["Both pairs mapped"][0] << "\t" << value["Both pairs mapped"][1] << "\t"
				res_table << value["Read 1"][0] << "\t" << value["Read 2"][0] << "\t"
				res_table << value["Singletons"][0] << "\t" << value["Singletons"][1] << "\t"
				res_table << value["Average insert size (absolute value)"][0] << "\t" << value["Median insert size (absolute value)"][0] << "\n"
			}
			new File("$with_name").write(res_table.toString())
		}
	}
}
