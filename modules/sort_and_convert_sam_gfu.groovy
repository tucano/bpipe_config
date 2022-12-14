// MODULE SORT SAM BY COORDINATES AND CONVERT TO BAM (rev1)

@preserve
sort_and_convert_sam_gfu =
{
    var pretend : false

    doc title: "Sort sam file by coordinates and convert to bam",
        desc: "This stage is used by htseq-count pipelines to reconvert the output reads sam file to a bam file",
        constraints: "I take the headers from the last forwarded bam file (the input of htseq-count stage)",
        author: "davide.rambaldi@gmail.com"

    requires SAMTOOLS: "Please define SAMTOOLS path"

    transform("reads.sam") to("reads_sorted.bam")
    {
        def command = """
            $SAMTOOLS view -H $input.bam | cat - $input.sam | $SAMTOOLS view -b -S - | $SAMTOOLS sort - $output.prefix
        """

        if (pretend)
        {
            println """
                INPUTS $input.bam, $input.sam
                OUTPUT: $output
                OUTPUT PREFIX: $output.prefix
                COMMAND: $command
            """

            command = """
                echo "INPUTS: $inputs" > $output
            """
        }
        exec command
    }
}
