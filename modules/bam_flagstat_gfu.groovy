// MODULE FLAGSTAT ON BAM

@preserve
bam_flagstat_gfu =
{
    var pretend    : false
    var sample_dir : false

    doc title: "samtools flagstat on BAM file",
        desc: """
            Launch $SAMTOOLS flagstat on ${input.bam}, produce a log file: $output.
            Main options with value:
                pretend    : $pretend
                sample_dir : $sample_dir
            With sample_dir true, this stage redefine output.dir using input.dir.
            Generate a .log file and forward ${input.bam} to next stage.
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    if (sample_dir) { output.dir = input.replaceFirst("/.*","") }

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
                echo "INPUT: $input" > $output;
            """
        }
        exec command
    }

    forward input.bam
}
