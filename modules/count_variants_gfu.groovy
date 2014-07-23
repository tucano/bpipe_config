// MODULE COUNT VARIANTS GFU

@preserve
count_variants_gfu =
{
  var pretend : false
  var output_dir : ""

  doc title: "Count variants in vcf file",
      desc: "",
      constraints: "",
      author: "davide.rambaldi@gmail.com"

  if (output_dir != "") output.dir = output_dir

  produce("variants_count.txt")
  {
    def command = """
      for i in $inputs;
      do
        FILE=`echo $i | sed 's/.*\\///' | sed 's/\\..*//'`;
        COUNT=`grep -cv "^#" $i`;
        echo -e "$FILE\t$COUNT" >> $output;
      done;
    """

    if (pretend)
    {
      println """
        INPUTS: $inputs
        OUTPUT: $output
        COMMAND: $command
      """
      command = "touch $output"
    }
    exec "$command"
  }
}
