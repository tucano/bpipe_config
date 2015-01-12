// MODULE MERGE BAM PREPARE(rev1)
import static groovy.json.JsonOutput.*

@preserve
merge_bam_by_header_prepare_gfu =
{
    doc title: "Prepare Merge bam files with $PICMERGE using bam headers SM",
        desc: "",
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    produce("merge_rule.txt","merge_rule.json")
    {
        exec """
            for i in ${inputs.bam};
            do SAMPLE=`$SAMTOOLS view -H \$i | grep SM | sed -e 's/.*SM://' | sed -e 's/CN:.*//'`;
            echo -e "\$i\t\$SAMPLE";
            done > merge_rule.txt
        """

        def bams = [:]
        new File(output.txt).eachLine { line ->
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

        def w = new File(output.json).newWriter()
        w << prettyPrint(toJson(bams))
        w.close()
    }
}
