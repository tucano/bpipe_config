// MODULE GENERATE TRUSEQ INTERVALS

@intermediate
generate_truseq_intervals_gfu =
{
    // stage vars
    var pretend : false

    doc title: "Generate per chromosome intervals from TruSeq file",
        desc: """
            This stage subdivide the intervals in the file $TRUSEQ per chromosome.
            TruSeq per chromosome intervals files will be used to as input for GATK UnifiedGenotyper.
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    produce ("${chr}.intervals")
    {
        def command = """
            grep "${chr}:" $TRUSEQ > $output;
        """

        if (pretend)
        {
            println """
                INPUT $inputs
                OUTPUT: $output
                CHROMOSOME: $chr
                COMMAND: $command
            """

            command = """
                echo "INPUTS: $inputs" > $output
            """
        }
        exec command
    }
}
