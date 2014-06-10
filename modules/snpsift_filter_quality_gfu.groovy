// MODULE SNPSIFT FILTER QUALITY GFU (Rev1)

snpsift_filter_quality_gfu =
{
    var pretend : false

    // INFO
    doc title: "SNP SIFT",
        desc: "SnpSift is a toolbox that allows you to filter and manipulate annotated files. Here we use snpsift to remove low quality variants",
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"

    def required_binds = ["SNPSIFT"]
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
