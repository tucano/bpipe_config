// MODULE FLAGSTAT MERGE

@preserve
flagstat_merge_gfu =
{
	var output_dir : "BAM"
	var pretend : false

	doc title: "Make a flagstats report from a set of flagstat logs",
      desc: "...",
      constraints: "Take BAM as input for pipeline consistency and forward them to the next stage",
      author: "davide.rambaldi@gmail.com"

	output.dir = output_dir

	def logs = inputs.collect { it.prefix + '.log' }

	if (pretend)
	{
		produce("flagstats.log")
		{
			println """
				INPUTS: $inputs
				OUTPUT: $output
			"""

			exec """
				mkdir -p $output_dir;
				touch $output;
			"""
		}
	}
	else
	{
		// BUILD FILE
		def res_table = new StringBuffer()
		res_table << "SAMPLE_NAME\tTOTAL_READS\tDUPLICATES\tMAPPED\tMAPPED(%)\tPAIRED\tREAD1\tREAD2\tPROPERLY_PAIRED\tPROPERLY_PAIRED(%)\tWITH_ITSELF_AND_MATE_MAPPED\tSINGLETONS\tSINGLETONS(%)\tWITH_MATE_MAPPED_TO_OTHER_CHR\tWITH_MATE_MAPPED_TO_OTHER_CHR_MAPQ5\n"

		logs.each { filename ->
			def file = new File(filename)
			def text = file.text
			def sample_name = file.getName().replaceFirst(/\..*/,'')
			def data_map = [:]
			res_table << sample_name
			text.eachLine { line ->
				if (line ==~ /.*total.*/)
				{
					data_map["total"] = line.replaceAll(/\sin.*/,"").split(/\s\+\s/)
				}
				else if (line ==~ /.*duplicates.*/)
				{
					data_map["duplicates"] = line.replaceAll(/\sduplicates.*/,"").split(/\s\+\s/)
				}
				else if (line ==~ /.*mapped\s\(.*/)
				{
					data_map["mapped"] = line.replaceAll(/\smapped.*/,"").split(/\s\+\s/)
					data_map["perc_mapped"] = line.replaceAll(/.*\(/,"").replaceAll(/\).*/,"").split(/:/)
				}
				else if (line ==~ /.*paired in sequencing.*/)
				{
					data_map["paired"] = line.replaceAll(/\spaired.*/,"").split(/\s\+\s/)
				}
				else if (line ==~ /.*read1.*/)
				{
					data_map["read1"] = line.replaceAll(/\sread1.*/,"").split(/\s\+\s/)
				}
				else if (line ==~ /.*read2.*/)
				{
					data_map["read2"] = line.replaceAll(/\sread2.*/,"").split(/\s\+\s/)
				}
				else if (line ==~ /.*properly paired.*/)
				{
					data_map["properly_paired"] = line.replaceAll(/\sproperly\spaired.*/,"").split(/\s\+\s/)
					data_map["properly_paired_perc"] = line.replaceAll(/.*\(/,"").replaceAll(/\).*/,"").split(/:/)
				}
				else if (line ==~ /.*with itself and mate mapped.*/)
				{
					data_map["itself_and_mate_mapped"] = line.replaceAll(/\swith.*/,"").split(/\s\+\s/)
				}
				else if (line ==~ /.*singletons.*/ )
				{
					data_map["singletons"] = line.replaceAll(/\ssingletons.*/,"").split(/\s\+\s/)
					data_map["singletons_perc"] = line.replaceAll(/.*\(/,"").replaceAll(/\).*/,"").split(/:/)
				}
				else if (line ==~ /.*with mate mapped to a different chr\s*$/)
				{
					data_map["other_chr"] = line.replaceAll(/\swith.*/,"").split(/\s\+\s/)
				}
				else if (line ==~ /.*mapQ.*/)
				{
					data_map["other_chr_mapQ"] = line.replaceAll(/\swith.*/,"").split(/\s\+\s/)
				}
			}
			res_table << "\t" << data_map["total"][0] << "\t" << data_map["duplicates"][0]
			res_table << "\t" << data_map["mapped"][0] << "\t" << data_map["perc_mapped"][0]
			res_table << "\t" << data_map["paired"][0] << "\t" << data_map["read1"][0] << "\t" << data_map["read2"][0]
			res_table << "\t" << data_map["properly_paired"][0] << "\t" << data_map["properly_paired_perc"][0]
			res_table << "\t" << data_map["itself_and_mate_mapped"][0] << "\t" << data_map["singletons"][0] << "\t" << data_map["singletons_perc"][0]
			res_table << "\t" << data_map["other_chr"][0] << "\t" << data_map["other_chr_mapQ"][0]
			res_table << "\n"
		}

		new File("flagstats.log").withWriter { out ->
			out.write res_table.toString()
		}

		produce("flagstats.log")
		{
			exec """
				mkdir -p $output_dir;
				cp flagstats.log ${output_dir}/
			"""
		}
	}
}

