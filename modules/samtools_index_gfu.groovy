// MODULE SAMTOOLS CREATE BAI FILE (rev1)

@preserve
samtools_index_gfu =
{
    var pretend : false

    doc title: "Create BAI index file from BAM file",
        desc: "Launch $SAMTOOLS index on $input.bam. Create link from bam.bai to .bai",
        constraints: "Forward input bam to next stage",
        author: "davide.rambaldi@gmail.com"

    def required_binds = ["SAMTOOLS"]
    def to_fail = false
    required_binds.each { key ->
        if (!binding.variables.containsKey(key))
        {
            to_fail = true
            println """
                This stage require this variable: $key, add this to the groovy file:
                    $key = "VALUE"
            """.stripIndent()
        }
    }
    if (to_fail) { System.exit(1) }

    transform("bai")
    {
        def command = """
            $SAMTOOLS index $input.bam;
            ln -s ${input}.bai $output;
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
        exec command
    }
    forward input.bam
}
