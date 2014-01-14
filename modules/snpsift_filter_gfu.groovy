// MODULE SNPSIFT GFU
SNPSIFT = "java -Xmx16g -jar /lustre1/tools/bin/SnpSift.jar"

snpsift_filter_gfu =
{
    var pretend : false

    // INFO
    doc title: "SNP SIFT",
        desc: "SnpSift is a toolbox that allows you to filter and manipulate annotated files.",
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"

    filter("dedup")
    {
        def command = """
            $SNPSIFT filter -f $input "((exists VQSLOD))" > $output;
            if [ -f ${output}.idx ]; then rm ${output}.idx; fi;
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
