// MODULE SNPSIFT ANNOTATE (rev1)

@intermediate
snpsift_annotate_gfu =
{
    var pretend : false

    doc title: "SnpSift annotate snps from $DBSNP in the vcf file",
        desc: """
            SnpSift is a toolbox that allows you to filter and manipulate annotated files.
            Main options with value:
                pretend    : $pretend
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    def required_binds = ["DBSNP","SNPSIFT"]
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

    filter("annotated")
    {
        def command = """
            $SNPSIFT annotate $DBSNP $input.vcf > $output.vcf
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
