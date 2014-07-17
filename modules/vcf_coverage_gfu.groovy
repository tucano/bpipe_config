// VCF COVERAGE GFU (rev1)

@preserve
vcf_coverage_gfu =
{
  var pretend       : false
  var output_dir    : ""
  var with_name     : "vcf_coverage.log"

  doc title: "Calculate coverage (nX) on VCF variants",
      desc: "...",
      constraints: "...",
      author: "davide.rambaldi@gmail.com"

  if (output_dir != "") output.dir = output_dir

  requires VCFUTILS : "Please define VCFUTILS path"
  requires SNPSIFT  : "Please define SNPSIFT path"

  produce("$with_name")
  {

    def command = """
      echo -e "SAMPLE\tCOVERAGE" > $output;
      $VCFUTILS listsam $input.vcf | while read sample;
      do echo -ne "$sample\t" >> $output;
      $VCFUTILS subsam $input.vcf $sample | $SNPSIFT filter "isVariant(GEN[0])" |
      $SNPSIFT extractFields - GEN[0].DP | awk '{sum+=\$1;n+=1} END {print sum/n}' >> $output;
      done;
    """

    if (pretend)
    {
      println """
        INPUT: $input.vcf
        OUTPUT: $output_filename
        COMMAND: $command
      """.stripIndent()

      command = "touch $output"
    }

    exec "$command","snpsift"
  }
}
