// MODULE VCF CONCAT (rev1)

@intermediate
vcf_concat_gfu =
{
    var pretend : false
    var with_suffix : ""

    doc title: "Vcf concat stage",
        desc: "Sort and concatenate VCF files",
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    def required_binds = ["VCFCONCAT","VCFSORT"]
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

    // remove chr from file name
    def output_prefix = "${input.vcf.prefix}".replaceAll(/\..*/,"")

    if (with_suffix != "")
    {
        produce("${output_prefix}.${with_suffix}.vcf")
        {
            def command = """
                $VCFCONCAT $inputs.vcf | $VCFSORT > $output;
            """

            if (pretend)
            {
                println """
                    INPUTS $inputs
                    VCF: $inputs.vcf
                    OUTPUT: $output
                    COMMAND: $command
                """

                command = """
                    echo "INPUT: $input" > $output
                """
            }
            exec command
        }
    }
    else
    {
        produce("${output_prefix}.vcf")
        {
            def command = """
                $VCFCONCAT $inputs.vcf | $VCFSORT > $output;
            """

            if (pretend)
            {
                println """
                    INPUTS $inputs
                    VCF: $inputs.vcf
                    OUTPUT: $output
                    COMMAND: $command
                """

                command = """
                    echo "INPUT: $input" > $output
                """
            }
            exec command
        }
    }
}
