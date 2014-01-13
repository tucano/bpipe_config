// MODULE GENERATE TRUSEQ INTERVALS

@intermediate
generate_truseq_intervals_gfu = 
{
    // stage vars
    var pretend : false

    doc title: "Generate per chromosome intervals from TruSeq file",
        desc: """
            TruSeq per chromosome intervals intermediate files can be used to make GATK analysis.
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
