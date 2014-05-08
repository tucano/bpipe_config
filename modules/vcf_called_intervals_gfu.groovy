// VCF CALLED INTERVALS

@preserve
vcf_called_intervals_gfu =
{
  var pretend       : false
  var output_dir    : ""

  doc title: "Calculate number of called intervals on VCF",
      desc: "...",
      constraints: "...",
      author: "davide.rambaldi@gmail.com"

  produce("vcf_called_intervals.log")
  {

    def output_filename

    if (output_dir != "")
    {
      output_filename = "${output_dir}/vcf_called_intervals.log"
    }
    else
    {
      output_filename = "vcf_called_intervals.log"
    }

    def command = """
      echo -e "CALLED\tNOT CALLED\t%CALLED" > $output;
      $VCFQUERY $input.vcf -f '%CHROM:%POS\\n' |
      awk -F ":" '{print \$1"\\t"\$2"\\t"\$2}' |
      intersectBed -a $INTERVALS_BED -b stdin -c |
      awk -F "\\t" '{ if (\$4>0) GOOD+=1; else BAD+=1} END { print GOOD"\\t"BAD"\\t"GOOD/(GOOD+BAD)*100}' >> $output
    """

    if (pretend)
    {
      println """
        INPUT: $input.vcf
        OUTPUT: $output_filename
        COMMAND: $command
      """.stripIndent()
      command = "touch $output_filename"
    }

    exec "$command"
  }
}
