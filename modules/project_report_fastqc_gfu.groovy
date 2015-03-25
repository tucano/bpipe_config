// MODULE PROJECT REPORT FASTQC (rev1)
import groovy.text.SimpleTemplateEngine

project_report_fastqc_gfu =
{
    var pretend : false

	doc title: "Create a project report dir for fastqc data",
		desc: "...",
		constraints: "...",
		author: "davide.rambaldi@gmail.com"

    if (pretend)
    {
        def project_report_dir = "Stupka_1_test_fastqc_report"
        output.dir = project_report_dir
        produce("index.html")
        {
            exec "touch ${project_report_dir}/index.html"
        }
    }
    else
    {
        // search for Project SampleSheet
        def samplesheet = new File("SampleSheet.csv")
        if (samplesheet.exists())
        {
            List lines = samplesheet.readLines()
            String[] headers = lines.remove(0).split(",")
            def samples = []
            lines.each { line ->
                def sample = line.split(",")
                def sample_map = [:]
                headers.eachWithIndex { header, i ->
                    sample_map.put(header, sample[i])
                }
                samples << sample_map
            }
            def unique_samples = []
            samples.each { sample ->
                unique_samples << sample["SampleID"]
            }
            unique_samples = unique_samples.unique()

            def project_report_dir = "${samples[0]["SampleProject"]}_fastqc_report"
            def report_dirs = inputs.collect{ it.replaceAll(/\/.*/,"") }.unique()
            def fastqc_html = inputs.collect{ it.replaceAll(/_data.txt/,"/fastqc_report.html") }
            def command = new StringBuffer()
            command << "mkdir -p $project_report_dir;"
            report_dirs.each { dir ->
                command << "cp -r $dir $project_report_dir/;"
            }

            // MAKE INDEX.HTML TEMPLATE
            def engine = new SimpleTemplateEngine()
            def html_index_template = """
            <html>
                <body>
                    <h1>FASTQC DATA FOR PROJECT: $project_name</h1>
                    <p>
                        <h3>SAMPLES</h3>
                        <table>
                            <tr>
                                <th>SampleID</th><th>TOTAL SEQUENCES</th><th>REPORTS</th>
                            </tr>
                            $samples_html
                        </table>
                    </p>
                </body>
            </html>
            """.stripIndent()

            // MAKE AN INDEX.HTML PAGE
            def samples_html = new StringBuffer()
            unique_samples.each { sample ->
                def reports_data = inputs.findAll{ inp -> inp ==~ /.*${sample}_.*/ }
                def total_seq_html = new StringBuffer()
                total_seq_html << "<table>"
                reports_data.each { r ->
                    def report_data = new File(r).readLines().get(6).replaceAll(/Total Sequences\s*(\d+)\s*/,"\$1")
                    def sequences = report_data.replaceAll(/.*Total Sequences\s*(\d*)\n.*/,"\$1").toInteger()
                    total_seq_html << "<tr><td>${r.replaceAll(/.*\/.*(L.*)_fastqc_data\.txt/,"\$1")}</td><td><strong>$report_data</strong></td></tr>"
                }
                total_seq_html << "</table>"

                def reports = fastqc_html.findAll{ report -> report ==~ /.*${sample}_.*/ }
                def reports_html = new StringBuffer()
                reports_html << "<table>"
                reports.each { r ->
                    reports_html << """
                        <tr><td><a href="${r}">${r.replaceAll(/.*\/(.*)\/.*/,"\$1")}</a></tr></td>
                    """.stripIndent()
                }
                reports_html << "</table>"

                samples_html << """
                    <tr>
                        <td>${sample}</td>
                        <td>$total_seq_html</td>
                        <td>$reports_html</td>
                    </tr>
                """.stripIndent()
            }
            def binding = [
                "project_name" : samples[0]["SampleProject"],
                "samples_html" : samples_html
            ]
            def html_index = engine.createTemplate(html_index_template).make(binding).toString()
            new File("${samples[0]["SampleProject"]}_fastqc_report.html").write(html_index)

            command << """
                mv ${samples[0]["SampleProject"]}_fastqc_report.html ${project_report_dir}/index.html
            """

            println """
                PROJECT: ${samples[0]["SampleProject"]}
                SAMPLES: $samples
                INPUTS: $inputs
                REPORT DIRS: $report_dirs
                FASTQC HTML: $fastqc_html
                COMMAND: $command
            """

            output.dir = project_report_dir
            produce("index.html")
            {
                exec "$command"
            }
        }
        else
        {
            println "Can't find SampleSheet in project directory ! Aborting ..."
            System.exit(1)
        }
    }
}
