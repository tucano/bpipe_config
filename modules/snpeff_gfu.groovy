// MODULE SNPEFF (rev1)

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

    requires SNPEFF : "Please define path for SNPEFF"
    requires SNPEFF_CONFIG : "Please define a SNPEFF_CONFIG file"

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
