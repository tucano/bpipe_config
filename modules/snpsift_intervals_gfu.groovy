// MODULE SNPSIFT INTERVALS (rev1)

@intermediate
snpsift_intervals_gfu =
{
    var pretend : false

    doc title: "SnpSift extract variants taht map in the intervals from $INTERVALS_BED in the vcf file",
        desc: """
            SnpSift is a toolbox that allows you to filter and manipulate annotated files.
            Main options with value:
                pretend    : $pretend
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    requires SNPSIFT : "Please define the path of SNPSIFT"
    requires INTERVALS_BED : "Please define the path of INTERVALS_BED file"

    filter("ontarget")
    {
        def command = """
            $SNPSIFT intervals -i $input.vcf $INTERVALS_BED > $output.vcf
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
