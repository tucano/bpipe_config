// MODULE SNPSIFT FILTER IMPACT GFU

snpsift_filter_impact_gfu =
{
    var pretend : false

    // INFO
    doc title: "SNP SIFT",
        desc: "SnpSift is a toolbox that allows you to filter and manipulate annotated files. Here we use snpsift to extract high and moderate impact variants",
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"

    requires SNPSIFT : "Please define the path of SNPSIFT"

    produce("Tier2.vcf")
    {
        def command = """
            $SNPSIFT filter -f $input.vcf "(ANN[*].IMPACT = 'HIGH') | (ANN[*].IMPACT = 'MODERATE') | (ANN[*].EFFECT = 'SPLICE_SITE_REGION')" > $output.vcf
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
        exec command, "snpsift"
    }
}
