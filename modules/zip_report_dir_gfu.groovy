// MODULE ZIP REPORT
@preserve
zip_report_dir_gfu =
{

    doc title: "Simply compress a report dir and remove it",
        desc: "...",
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    produce("${input}.zip") {
        println "INPUT: $input $OUTPUT: $output"
        exec """
            zip -r ${input}.zip ${input}/;
            rm -rf ${input};
        """
    }
}
