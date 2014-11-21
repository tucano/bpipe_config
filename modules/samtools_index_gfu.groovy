// MODULE SAMTOOLS CREATE BAI FILE (rev1)

@preserve
samtools_index_gfu =
{
    var pretend : false
    var output_dir : ""

    if (output_dir != "") output.dir = output_dir

    doc title: "Create BAI index file from BAM file",
        desc: "Launch $SAMTOOLS index on $input.bam. Create link from bam.bai to .bai",
        constraints: "Forward input bam to next stage",
        author: "davide.rambaldi@gmail.com"

    requires SAMTOOLS: "Please define SAMTOOLS path"

    transform("bai")
    {
        def command = new StringBuffer()
        if (output_dir) command << "mkdir -p $output.dir;"

        command << """
            $SAMTOOLS index $input.bam ${input.prefix}.bam.bai;
            rm ${output};
            ln -s ${input.prefix}.bam.bai $output;
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
        exec "$command"
    }
    forward input.bam
}
