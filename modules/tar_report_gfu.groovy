// MODULE TAR REPORT GFU

@preserve
tar_report_gfu =
{
  var pretend : false

  doc title: "Make a flagstats report from a set of flagstat logs",
      desc: "...",
      constraints: "Take BAM as input for pipeline consistency and forward them to the next stage",
      author: "davide.rambaldi@gmail.com"

  requires CUSTOM_CSS      : "Please define path of report.css"
  requires README_TEMPLATE : "Please define path of README template"
  requires REPORT_DATA_DIR : "Please define path of REPORT_DATA_DIR"

  produce("README","report.css","report.tar.gz")
  {
    def command = """
      cp $README_TEMPLATE README;
      cp $CUSTOM_CSS $output2;
      tar -czvf report.tar.gz $input.Rmd $REPORT_DATA_DIR $output1 $output2;
    """

    if (pretend)
    {
      println """
        INPUTS: $input
        OUTPUT: $output
        COMMAND: $command
      """
      command = """
        touch $output1 $output2 $output3
      """
    }
    exec "$command"
  }
}
