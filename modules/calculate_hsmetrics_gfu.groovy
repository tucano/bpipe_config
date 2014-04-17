// MODULE HSMETRICS

@intermediate
calculate_hsmetrics_gfu =
{
	var pretend    : false
	var output_dir : ""

	doc title: "Calculates a set of HS metrics from a sam or bam file",
        desc: "...",
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    if (output_dir != "") output.dir = output_dir

   	transform("hsmetrics") 
   	{
   		def command = new StringBuffer()

   		if (output_dir != "") {
   			command << "mkdir -p $output_dir;"
   		}

   		command << """
			$HSMETRICS BI=$BAITS TI=$TARGETS I=$input.bam O=$output REFERENCE_SEQUENCE=$REFERENCE_GENOME_FASTA
		"""

		if (pretend)
		{
			command = "touch $output"
			println """
				INPUT_BAM       : $input.bam
				OUTPUT          : $output
				REFERENCE_FASTA : $REFERENCE_GENOME_FASTA
				BAITS           : $BAITS       
				TARGETS         : $TARGETS
				COMMAND         : $command
			"""
		}

		exec "$command", "hsmetrics"
   	}
}