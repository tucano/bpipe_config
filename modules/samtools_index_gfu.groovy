// MODULE SAMTOOLS CREATE BAI FILE (rev1)

@preserve
samtools_index_gfu =
{
    var pretend : false

    doc title: "Create BAI index file from BAM file",
        desc: "Launch $SAMTOOLS index on $input.bam. Create link from bam.bai to .bai",
        constraints: "Forward input bam to next stage",
        author: "davide.rambaldi@gmail.com"

    requires SAMTOOLS: "Please define SAMTOOLS path"

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
