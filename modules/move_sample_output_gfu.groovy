// MODULE MOVE SAMPLE RESULTS (rev1)

@preserve
move_sample_output_gfu =
{
    var result_dir : "BAM"
    var pretend    : false

    doc title: "Move outputs from pipelines from sample working dir to results dir",
        desc: """
            By default move files to a directory named: BAM.
            Directory is created if not exists (mkdir -p).
            This stage generate a soft link in sample working dir with TARGET the output file in result dir.
            The soft link is used by bpipe to keep track of intermediate files.
        """,
        constraints: """
            If input is bam, automatically move also bai files.
        """,
        author: "davide.rambaldi@gmail.com"

    def outputs = []
    inputs.each { file ->
        def filename = file.replaceFirst(/.*\//,"")
        outputs << filename
        if (filename.endsWith('.bam')) { outputs << "${filename.prefix}.bai" }
    }

    output.dir = result_dir

    produce(outputs)
    {
        def command = """
            mkdir -p $result_dir;
        """

        inputs.each { file ->
            command += "mv $file $result_dir/; ln -s ../${result_dir}/${file.replaceFirst(/.*\//,"")} ${file};"
            if (file.endsWith('.bam'))
            {
                command += "mv ${file.prefix}.bai $result_dir/; ln -s ../${result_dir}/${file.prefix.replaceFirst(/.*\//,"")}.bai ${file.prefix}.bai;"
            }
        }

        if (pretend)
        {
            println """
                INPUT: $inputs
                OUTPUT: $outputs [in dir ${output.dir}]
                COMMAND: $command
            """
            command = ""
            outputs.each { file ->
                command += "touch ${result_dir}/${file};"
            }
        }
        exec command
    }
}
