// MODULE BAMTOOLS STATS

@intermediate
bamtools_stats_gfu =
{
  var pretend         : false
  var output_dir      : ""
  var use_sample_name : ""

  doc title: "Calculates a set of stats from a bam file",
      desc: "...",
      constraints: "...",
      author: "davide.rambaldi@gmail.com"

  if (output_dir != "") output.dir = output_dir

  transform("bamstats")
  {
    def command = new StringBuffer()

    if (output_dir) command << "mkdir -p $output.dir;"

    if (use_sample_name != "")
    {
      command << """
        SAMPLE_NAME=`samtools view -H $input.bam | grep @RG | head -n 1 | sed -e 's/.*SM://' | sed -e 's/\\s.*//'`;
        $BAMTOOLS stats -insert -in $input.bam > ${output.dir}/${SAMPLE_NAME}.bamstats;
      """
    }
    else
    {
      command << """
        $BAMTOOLS stats -insert -in $input.bam > $output;
      """
    }

    if (pretend)
    {
      println """
        INPUT: $input
        OUTPUT: $output
        COMMAND: $command
      """
      command = ""
      if (output_dir) {
        command << "mkdir -p $output_dir;"
      }
      command = "touch $output;"
    }
    exec "$command", "bamtools"
  }
}
