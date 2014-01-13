// MODULE FLAGSTAT ON BAM
SAMTOOLS="/usr/local/cluster/bin/samtools"

@preserve
bam_flagstat_gfu =
{
    var pretend : false

    doc title: "samtools flagstat on BAM file",
        desc: """
            Launch $SAMTOOLS flagstat on ${input.bam}, produce a log file: $output.
            Main options with default value:
                pretend    : $pretend

            Generate a .log file and forward ${input.bam} to next stage.
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"


    transform("log") 
    {
        def command = """
            $SAMTOOLS flagstat $input.bam > $output;
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
                echo "INPUT: $input" > $output
            """
        }
        exec command
    }

    forward input.bam
}
