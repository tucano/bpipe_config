// MODULE VCF 2 XLS (rev1)

@preserve
vcf_to_xls_gfu =
{
    var pretend : false

    // INFO
    doc title: "VCF TO XLS",
        desc: "Convert VCF to XLS",
        constraints: "For any question, write to cittaro.davide@hsr.it",
        author: "davide.rambaldi@gmail.com"

    def required_binds = ["VCF2XLS","VCF2XLS_ANNOTATION"]
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

    transform("xls")
    {
        def command = """
            $VCF2XLS -v $input.vcf -o $output.xls -f DP MQ VQSLOD AC AF CAF COMMON dbNSFP_ESP6500_AA_AF dbNSFP_Ancestral_allele Phi_score Phi_class dbNSFP_Polyphen2_HVAR_pred dbNSFP_SIFT_score OM MUT -d $VCF2XLS_ANNOTATION
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
