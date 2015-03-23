// MODULE GENERATE TRUSEQ INTERVALS (rev1)

@intermediate
generate_truseq_intervals_gfu =
{
    // stage vars
    var pretend   : false
    var chr_names : true

    doc title: "Generate per chromosome intervals from Intervals file",
        desc: """
            This stage subdivide the intervals in the file $INTERVALS per chromosome.
            Per chromosome intervals files will be used as input for GATK UnifiedGenotyper.
            Main options with value:
                pretend   : $pretend
                INTERVALS : $INTERVALS
                chr_names : $chr_names   (chromosome names use 'chr' as prefix?)
        """,
        constraints: "Note that for hs37d5 like genomes where chr are ONLY numbers (without chr), you should use ",
        author: "davide.rambaldi@gmail.com"

    requires INTERVALS: "Please define an INTERVALS file"

    produce ("${chr}.intervals")
    {
        def grep_string = chr_names ? chr : chr.replaceAll("chr","")
        def command = """
            grep "${grep_string}:" $INTERVALS > $output;
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
