// MODULE HEALTY EXOMES INFO GFU

@intermediate
healty_exomes_info_gfu =
{
  var pretend : false
  var output_dir : ""

  doc title: "Check healty exomes info",
      desc: "For now just filenames and count",
      constraints: "",
      author: "davide.rambaldi@gmail.com"

  if (output_dir != "") output.dir = output_dir

  requires HEALTY_EXOMES_DIR : "Please define path of HEALTY_EXOMES_DIR"

  produce("healty_exomes.txt")
  {
    def command = """
      for i in ${HEALTY_EXOMES_DIR}/*.bam; do echo \$i | sed 's/.*\\///' | sed 's/\\..*//' >> $output; done;
    """

    if (pretend)
    {
      println """
        OUTPUT: $output
        COMMAND: $command
      """
      command = "touch $output"
    }

    exec "$command"
  }
}
