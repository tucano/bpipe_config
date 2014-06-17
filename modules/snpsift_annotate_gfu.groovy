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

    requires DBSNP : "Please define path for DBSNP file"
    requires SNPSIFT : "Please define the path of SNPSIFT"

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
