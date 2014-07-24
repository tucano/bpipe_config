// MODULE TAR REPORT GFU

@preserve
tar_wes_report_gfu =
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

  requiers BAM_RECALIBRATION_SCHEMA   : "Please define path of BAM_RECALIBRATION_SCHEMA image"
  requires VARIANTS_CALLING_SCHEMA    : "Please define path of VARIANTS_CALLING_SCHEMA image"
  requires VARIANTS_ANNOTATION_SCHEMA : "Please define path of VARIANTS_ANNOTATION_SCHEMA image"

  produce("report.tar.gz")
  {
    // modify image paths with sed
    def command = """
      cp $README_TEMPLATE README;
      cp $CUSTOM_CSS report.css;
      cp $STYLE_TEMPLATE Style.R;
      cp $BAM_RECALIBRATION_SCHEMA BAM_RECALIBRATION_SCHEMA.png;
      cp $VARIANTS_CALLING_SCHEMA VARIANTS_CALLING_SCHEMA.png;
      cp $VARIANTS_ANNOTATION_SCHEMA VARIANTS_ANNOTATION_SCHEMA.png;
      sed -i 's/$BAM_RECALIBRATION_SCHEMA/BAM_RECALIBRATION_SCHEMA.png/' $input.Rmd;
      sed -i 's/$VARIANTS_CALLING_SCHEMA/VARIANTS_CALLING_SCHEMA.png/' $input.Rmd;
      sed -i 's/$VARIANTS_ANNOTATION_SCHEMA/VARIANTS_ANNOTATION_SCHEMA.png/' $input.Rmd;
      tar -czvf SampleSheet.csv SampleSheet.csv $input.Rmd $REPORT_DATA_DIR report.css Style.R README BAM_RECALIBRATION_SCHEMA.png VARIANTS_CALLING_SCHEMA.png VARIANTS_ANNOTATION_SCHEMA.png;
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
