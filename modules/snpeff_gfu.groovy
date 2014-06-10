// MODULE SNPEFF

snpeff_gfu =
{
    var pretend : false

    doc title: "SnpEff: Genetic variant annotation and effect prediction toolbox. ",
        desc: """
            Genetic variant annotation and effect prediction toolbox. It annotates and predicts the effects of variants on genes (such as amino acid changes).
            Main options with value:
                pretend    : $pretend
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    def required_binds = ["SNPEFF","SNPEFF_CONFIG"]
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

    produce("Tier0.vcf")
    {
        def command = """
            $SNPEFF eff -c $SNPEFF_CONFIG -canon -no-upstream -no-downstream -no-intergenic GRCh37.70 $input.vcf > $output
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
