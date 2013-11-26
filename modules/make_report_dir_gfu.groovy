// MODULE  GENERATE REPORT DIR
make_report_dir_gfu =
{
    doc title: "Simply create a report dir and copy SampleSheet.csv in it",
        desc: "...",
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    def custom_output = "${input.replaceAll(/\/$/,"")}_report"
    produce("$custom_output") {
        println "INPUT: $input, OUTPUT: $output, Making dir $custom_output"
        exec """
            mkdir -p $custom_output;
            cp ${input}/SampleSheet.csv ${custom_output}/;
        """
    }
}
