// MODULE TAR REPORT GFU

@preserve
tar_report_gfu =
{
  var pretend : false

  doc title: "Make a flagstats report from a set of flagstat logs",
      desc: "...",
      constraints: "...",
      author: "davide.rambaldi@gmail.com"

  requires CUSTOM_CSS      : "Please define path of report.css"
  requires README_TEMPLATE : "Please define path of README template"
  requires REPORT_DATA_DIR : "Please define path of REPORT_DATA_DIR"
  requires STYLE_TEMPLATE  : "Please define path of STYLE_TEMPLATE"

  produce("README","report.css","Style.R","report.tar.gz")
  {
    def command = """
      cp $README_TEMPLATE $output1;
      cp $CUSTOM_CSS $output2;
      cp $STYLE_TEMPLATE $output3;
      tar -czvf SampleSheet.csv $output4 $input.Rmd $REPORT_DATA_DIR $output1 $output2 $output3;
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
