// MODULE  GENERATE REPORT DIR (rev1)

@preserve
make_report_dir_gfu =
{
    doc title: "Simply create a report dir and copy SampleSheet.csv in it",
        desc: "...",
        constraints: "The new version use input.json and branch.sample",
        author: "davide.rambaldi@gmail.com"

    // THIS STAGE COMES FROM sample_dir_gfu, so I have a branch.sample
    // and receive as input SampleSheet.csv e setstripe.log

    def outputs = [
        ("${branch.sample}_report")
    ]

    produce(outputs)
    {
        exec """
            mkdir -p $output;
            cp ${input.csv} ${output}/;
        """
    }
}
