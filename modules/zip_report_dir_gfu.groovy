// MODULE ZIP REPORT

@preserve
zip_report_dir_gfu =
{

    var result_dir : "FASTQC"

    doc title: "Simply compress a report dir and move the zip to result dir",
        desc: "...",
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    output.dir = result_dir

    produce("${input}.zip")
    {
        exec """
            mkdir -p $result_dir;
            zip -r ${input}.zip ${input}/;
            mv ${input}.zip $result_dir/;
            rm -rf ${input};
        """
    }
}
