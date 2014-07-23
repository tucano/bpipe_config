// MODULE BWA SOFTWARE VERSION

@intermediate
bwa_software_version_gfu =
{
  var pretend : false
  var output_dir : ""

  doc title: "Check bwa version",
      desc: "",
      constraints: "",
      author: "davide.rambaldi@gmail.com"

  if (output_dir != "") output.dir = output_dir

  requires BWA : "Please define path of BWA"

  produce("bwa_version.txt")
  {
    def command = "$BWA 2>&1 | grep Version | awk '{ print \$2 }' > $output"

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
