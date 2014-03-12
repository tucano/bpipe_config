// MODULE GENERATE TRUSEQ INTERVALS

@intermediate
generate_truseq_intervals_gfu =
{
    // stage vars
    var pretend : false

    doc title: "Generate per chromosome intervals from Intervals file",
        desc: """
            This stage subdivide the intervals in the file $INTERVALS per chromosome.
            Per chromosome intervals files will be used as input for GATK UnifiedGenotyper.
            Main options with value:
                pretend   : $pretend
                INTERVALS : $INTERVALS
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    produce ("${chr}.intervals")
    {
        def command = """
            grep "${chr}:" $INTERVALS > $output;
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
