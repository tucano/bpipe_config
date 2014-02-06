// MODULE SNPSIFT FILTER QUALITY GFU
SNPSIFT = "java -Xmx16g -jar /lustre1/tools/bin/SnpSift.jar"

snpsift_filter_quality_gfu =
{
    var pretend : false

    // INFO
    doc title: "SNP SIFT",
        desc: "SnpSift is a toolbox that allows you to filter and manipulate annotated files. Here we use snpsift to remove low quality variants",
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"


    filter("quality")
    {
        def command = """
            $SNPSIFT filter -f $input.vcf "FILTER = 'PASS' | FILTER = 'VQSRTrancheSNP99.00to99.90'" > $output.vcf
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
