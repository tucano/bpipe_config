// MODULE SNPSIFT INTERVALS
SNPSIFT="java -jar /lustre1/tools/bin/SnpSift.jar"

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
