// MODULE HSMETRICS (rev1)

@intermediate
calculate_hsmetrics_gfu =
{
	var pretend    : false
	var output_dir : ""
  var use_sample_name : ""

	doc title: "Calculates a set of HS metrics from a sam or bam file",
        desc: "...",
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    requires HSMETRICS : "Please define path of HSMETRICS"
    requires REFERENCE_GENOME_FASTA: "Please define a REFERENCE_GENOME_FASTA"
    requires BAITS: "Please define a BAITS file"
    requires TARGETS: "Please define a TARGETS file"

    if (output_dir != "") output.dir = output_dir

    // with use_sample_name ASSUME THE FILE HAVE THE SM NAME (otherwise will fail)
   	transform("hsmetrics")
   	{
   		def command = new StringBuffer()
      def output_filename

   		if (output_dir != "") {
   			command << "mkdir -p $output_dir;"
   		}

      if (use_sample_name != "")
      {
        command << """
           SAMPLE_NAME=`samtools view -H $input.bam | grep @RG | head -n 1 | sed -e 's/.*SM://' | sed -e 's/\\s.*//'`;
           $HSMETRICS BI=$BAITS TI=$TARGETS I=$input.bam O=${SAMPLE_NAME}.hsmetrics REFERENCE_SEQUENCE=$REFERENCE_GENOME_FASTA;
        """
      }
      else
      {
        command << """
          $HSMETRICS BI=$BAITS TI=$TARGETS I=$input.bam O=$output REFERENCE_SEQUENCE=$REFERENCE_GENOME_FASTA
        """
      }

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
