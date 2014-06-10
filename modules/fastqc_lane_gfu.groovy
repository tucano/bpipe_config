// MODULE FASTQC LANE (rev1)

@preserve
fastqc_lane_gfu =
{
    // stage vars
    var paired  : true
    var pretend : false

    doc title: "Fastqc on fastq.gz files in the current directory",
        desc: """
            This module is for current working directory,
            use fastqc_sample_gfu for multiple samples (see also fastqc_project pipeline).
            Produce a zip files with fastqc quality control.
            Main options with value:
            pretend          : $pretend
            paired           : $paired
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    requires FASTQC : "Please define path of FASTQC"

    if (paired)
    {
        def output_name = input.prefix.replaceAll(/_R[1-2]_[0-9]*.fastq/,"")

        from("*.fastq.gz") produce("${output_name}_R1_fastqc.zip","${output_name}_R2_fastqc.zip")
        {
            def command = """
                $FASTQC -f fastq --noextract --casava --nogroup -t 4 -o . $inputs
            """

            if (pretend)
            {
                println """
                    INPUTS:  $inputs
                    OUTPUT:  $outputs
                    COMMAND:
                        $command
                """
                command = """
                    echo "INPUTS: $inputs" > $output1;
                    echo "INPUTS: $inputs" > $output2;
                """
            }

            exec command, "fastqc"
        }
    }
    else
    {
        def output_name = input.prefix.replaceAll(/_[0-9]*.fastq/,"")

        from("*.fastq.gz") produce("${output_name}_fastqc.zip")
        {

            def command = """
                $FASTQC -f fastq --noextract --casava --nogroup -t 4 -o . $inputs
            """

            if (pretend)
            {
                println """
                    INPUTS:  $inputs
                    OUTPUT:  $output
                    COMMAND:
                        $command
                """
                command = """
                    echo "INPUTS: $inputs" > $output
                """
            }

            exec command, "fastqc"
        }
    }
}
