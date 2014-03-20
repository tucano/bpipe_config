// MODULE  GENERATE REPORT DIR

@preserve
make_report_dir_gfu =
{
    doc title: "Simply create a report dir and copy SampleSheet.csv in it",
        desc: "...",
        constraints: "The new version require as input the fastq.gz files from sample_dir_gfu",
        author: "davide.rambaldi@gmail.com"

    def outputs = [
        (input.dir.replaceAll(/\/$/,"").replaceFirst(/.*\//,"") + "_report")
    ]

    produce(outputs)
    {
        exec """
            mkdir -p $output;
            cp ${input.dir}/SampleSheet.csv ${output}/;
        """
    }
}
