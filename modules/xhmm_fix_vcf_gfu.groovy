// MODULE XHMM FIX VCF

@preserve
xhmm_fix_vcf_gfu =
{
    var pretend : false

    doc title: "XHMM: genotype cnv",
        desc: """
            CONFIGURATION:
            pretend : $pretend
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    def required_binds = ["XHMM_FIX_VCF","REFERENCE_GENOME_FASTA"]
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

    filter("fixed")
    {
        def command = """
            $XHMM_FIX_VCF $input.vcf $REFERENCE_GENOME_FASTA > $output.vcf
        """

        if (pretend)
        {
            println """
                INPUT: $input
                OUTPUT: $output
                COMMAND: $command
            """
            command = "touch $output"
        }

        exec "$command", "xhmm"
    }
}
