// MODULE MERGE BAM PREPARE(rev1)
import static groovy.json.JsonOutput.*

merge_bam_by_header_json_gfu =
{
    doc title: "Prepare Merge bam files with $PICMERGE using bam headers SM, make json file",
        desc: "",
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    produce("merge_rule.json")
    {
        def bams = [:]
        file(input.txt).eachLine { line ->
            data = line.split("\t")
            if (bams[data[1]])
            {
                bams[data[1]].push data[0]
            }
            else
            {
                bams[data[1]] = [data[0]]
            }
        }

        def w = file(output.json).newWriter()
        w.write prettyPrint(toJson(bams))
    }
}
