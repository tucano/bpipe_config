// MODULE SNPSIFT FILTER DUPLICATES GFU (rev1)

@preserve
snpsift_filter_duplicates_gfu =
{
    var pretend : false

    // INFO
    doc title: "SNP SIFT",
        desc: "SnpSift is a toolbox that allows you to filter and manipulate annotated files. Here we use snpsift to remove duplicates using ((exists VQSLOD))",
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"

    requires SNPSIFT : "Please define the path of SNPSIFT"

    filter("dedup")
    {
        def command = """
            $SNPSIFT filter -f $input "((exists VQSLOD))" > $output;
            if [ -f ${output}.idx ]; then rm ${output}.idx; fi;
        """

        if (pretend)
        {
            println """
                INPUT:   $input
                OUTPUT:  $output
                COMMAND: $command
            """

            command = """
                echo "INPUT: $input" > $output
            """
        }
        exec command, "snpsift"
    }
}
