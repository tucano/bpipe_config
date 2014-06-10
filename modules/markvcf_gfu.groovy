// MODULE MARKVCF (Rev1)

markvcf_gfu =
{
    var pretend : false

    // INFO
    doc title: "MARKVCF",
        desc: "Mark VCF files for gene properties",
        constraints: "For any question, write to cittaro.davide@hsr.it",
        author: "davide.rambaldi@gmail.com"

    def required_binds = ["MARKVCF","SQL_GENES_TABLE","PHI_SCORES"]
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

    produce("Tier1.vcf")
    {
        def command = """
            $MARKVCF -v $input.vcf -r $SQL_GENES_TABLE -g $PHI_SCORES -n Phi_ > $output.vcf
        """

        if (pretend)
        {
            println """
                INPUT: $input
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
