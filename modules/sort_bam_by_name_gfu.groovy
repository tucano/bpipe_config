// MODULE SORT BAM BY NAME FILE GFU (rev1)

@intermediate
sort_bam_by_name_gfu =
{
    var pretend : false

    doc title: "Samtools: sort by name bam file",
        desc: "Sort bam file by name",
        constrains: "...",
        author: "davide.rambaldi@gmail.com"

    requires SAMTOOLS: "Please define SAMTOOLS path"

    filter("sorted_by_name")
    {
        def command = """
            $SAMTOOLS sort -n $input.bam $output.prefix
        """

        if (pretend)
        {
            println """
                INPUT:  $input
                OUTPUT: $output
                OUTPUT PREFIX: $output.prefix
                COMMAND: $command
            """

            command = """
                echo "INPUT: $input" > $output
            """
        }
        exec command, "samtools"
    }
}
