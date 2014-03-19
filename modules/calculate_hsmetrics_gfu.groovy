// MODULE HSMETRICS

@intermediate
calculate_hsmetrics_gfu =
{
	var pretend : false

	doc title: "Calculates a set of HS metrics from a sam or bam file",
        desc: "...",
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

   	transform("hsmetrics") 
   	{
   		def command = """
			$HSMETRICS BI=$BAITS TI=$TARGETS I=$input.bam O=$output REFERENCE_SEQUENCE=$REFERENCE_GENOME_FASTA
		"""

		if (pretend)
		{
			println """
				INPUT_BAM       : $input.bam
				OUTPUT          : $output
				REFERENCE_FASTA : $REFERENCE_GENOME_FASTA
				BAITS           : $BAITS       
				TARGETS         : $TARGETS
				COMMAND         : $command
			"""
			command = "touch $output"
		}

		exec "$command", "hsmetrics"
   	}
}