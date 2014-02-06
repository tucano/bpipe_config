// MODULE VCF 2 XLS
VCF2XLS = "/usr/local/cluster/python2.7/bin/python2.7 /lustre1/tools/bin/vcf2xls.py"

vcf_to_xls_gfu =
{
    var pretend : false

    // INFO
    doc title: "VCF TO XLS",
        desc: "Convert VCF to XLS",
        constraints: "For any question, write to cittaro.davide@hsr.it",
        author: "davide.rambaldi@gmail.com"


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
