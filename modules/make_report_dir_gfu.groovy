// MODULE  GENERATE REPORT DIR

@preserve
make_report_dir_gfu =
{
    doc title: "Simply create a report dir and copy SampleSheet.csv in it",
        desc: "...",
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    def outputs = [
        (input.replaceAll(/\/$/,"") + "_report")
    ]
    
    produce(outputs) 
    {
        exec """
            mkdir -p $output;
            cp ${input}/SampleSheet.csv ${output}/;
        """
    }
}
