// MODULE BEDTOOL FILTER INTERVALS GFU

@intermediate
bedtools_filter_intervals_gfu =
{
  var pretend : false

  doc title: "SnpSift extract variants taht map in the intervals from $INTERVALS_BED in the vcf file",
        desc: """
            SnpSift is a toolbox that allows you to filter and manipulate annotated files.
            Main options with value:
                pretend    : $pretend
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

  requires BEDTOOLS : "Please define the path of BEDTOOLS"
  requires INTERVALS_BED : "Please define the path of INTERVALS_BED file"

  filter("ontarget")
  {
    def command = """
      $BEDTOOLS intersect -a $input.vcf -b $INTERVALS_BED -u -wa -header > $output.vcf
    """

    if (pretend)
    {
        println """
            INPUT:  $input
            OUTPUT: $output
            COMMAND:
                $command
        """
        command = """
            echo "INPUT: $input" > $output;
        """
    }
    exec command
  }
}
