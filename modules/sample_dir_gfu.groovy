// MODULE SAMPLE DIR GFU

@intermediate
sample_dir_gfu =
{
    doc title: "Preapare working dir for a sample and set lustre options for the directory",
        desc: """
            Lustre options:
                -c -1 : a stripe_count of -1 means to stripe over all available OSTs.
                -i -1 : a start_ost_index of -1 allows the MDS to choose the starting index and it is strongly recommended, as this allows space and load balancing to be done by the MDS as needed.
                -s 2M : Stripsize 2 megabytes
        """,
        constraints: "setstripe and getstripe as non blocking steps (Fails in non lustre fs, but will return always true).",
        author: "davide.rambaldi@gmail.com"

    def outputs = []
    def output_dir = input.dir.replaceFirst(/.*\//,"")
    output.dir = output_dir
    def dataDir = new File(input.dir)

    // make sample dir and copy SampleSheet.csv
    new File(output_dir).mkdir()
    ['cp', "${input.dir}/SampleSheet.csv", "$output_dir"].execute().waitFor()

    // copy gfu_envrinoment.sh
    if (new File("${input.dir}/gfu_envrinoment.sh").exists())
    {
        ['cp', "${input.dir}/gfu_envrinoment.sh", "$output_dir"].execute().waitFor()
    }

    // Link fastq.gz files
    dataDir.eachFile { file ->
        if (file.getName().endsWith(".fastq.gz"))
        {
            def targetLink = "${output_dir}/${file.getName()}"
            ['ln', '-s', file.absolutePath, targetLink ].execute().waitFor()
            outputs << file.getName()
        }
    }

    // ADD setstripe.log to outputs
    outputs << "setstripe.log"

    produce(outputs)
    {
        exec """
            $LSF setstripe -c -1 -i -1 -s 2M $output_dir 1>/dev/null 2>&1 || true;
            $LSF getstripe -d $output_dir > ${output_dir}/setstripe.log 2>&1 || true;
        """
    }
}
