// MODULE GENE COVERAGE FROM RSEQC

@preserve
samtools_idxstats_gfu =
{
    var pretend : false

    doc title: "Samtools idxstats on bam file (index)",
        desc: """
            Retrieve and print stats in the index file.
            The output is TAB delimited with each line consisting of reference sequence name,
            sequence length, # mapped reads and # unmapped reads.
        """,
        constrains: "BAM file $input.bam must be indexed",
        author: "davide.rambaldi@gmail.com"

    transform("idxstats.log")
    {
        def command = """
            $SAMTOOLS idxstats $input.bam > $output
        """

        if (pretend)
        {
            println """
                INPUT $input
                OUTPUTS: $output
                COMMAND: $command
            """

            command = """
                echo "INPUT: $input" > $output
            """
        }
        exec command
    }
    forward input.bam
}
