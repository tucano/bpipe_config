// VCF COVERAGE GFU (rev1)

@preserve
vcf_coverage_gfu =
{
  var pretend       : false
  var output_dir    : ""

  doc title: "Calculate coverage (nX) on VCF variants",
      desc: "...",
      constraints: "...",
      author: "davide.rambaldi@gmail.com"

  if (output_dir != "") output.dir = output_dir

  def required_binds = ["VCFUTILS","SNPSIFT"]
  def to_fail = false
  required_binds.each { key ->
    if (!binding.variables.containsKey(key))
    {
        to_fail = true
        println """
            This stage require this variable: $key, add this to the groovy file:
                $key = "VALUE"
        """.stripIndent()
    }
  }
  if (to_fail) { System.exit(1) }

  produce("vcf_coverage.log")
  {
    def output_filename

    if (output_dir != "")
    {
      output_filename = "${output_dir}/vcf_coverage.log"
    }
    else
    {
      output_filename = "vcf_coverage.log"
    }

    def command = """
      echo -e "SAMPLE\tCOVERAGE" > $output_filename;
      $VCFUTILS listsam $input.vcf | while read sample;
      do echo -ne "$sample\t" >> $output_filename;
      $VCFUTILS subsam all_samples.vcf $sample | $SNPSIFT filter "isVariant(GEN[0])" |
      $SNPSIFT extractFields - GEN[0].DP | awk '{sum+=\$1;n+=1} END {print sum/n}' >> $output_filename;
      done;
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

    exec "$command","snpsift"
  }
}
