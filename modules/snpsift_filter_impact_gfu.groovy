// MODULE SNPSIFT FILTER IMPACT GFU
SNPSIFT = "java -Xmx16g -jar /lustre1/tools/bin/SnpSift-3.5c.jar"

snpsift_filter_impact_gfu =
{
    var pretend : false

    // INFO
    doc title: "SNP SIFT",
        desc: "SnpSift is a toolbox that allows you to filter and manipulate annotated files. Here we use snpsift to extract high and moderate impact variants",
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"

    produce("Tier2.vcf")
    {
        def command = """
            $SNPSIFT filter -f $input.vcf "EFF[*].IMPACT = 'HIGH' | EFF[*].IMPACT = 'MODERATE'" > $output.vcf
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
