// MODULE MOVE SAMPLE RESULTS

@preserve
move_sample_results =
{
    var result_dir : "BAM"
    var pretend : true

    doc title: "Move results (final outputs) from sample working dir to results dir",
        desc: """
            By default move files to a directory named: BAM.
            Directory is created if not exixts (mkdir -p)
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    def outputs = []
    inputs.each { file ->
        outputs << file.replaceFirst(/.*\//,"")
    }

    output.dir = result_dir

    produce(outputs)
    {
        def command = """
            mkdir -p $result_dir;
            mv $inputs $result_dir/;
        """

        if (pretend)
        {
            println """
                INPUT: $inputs
                OUTPUT: $output
                COMMAND: $command
            """
            command = "touch $output"
        }
        exec command
    }
}
