// MODULE MERGE BAM PREPARE(rev1)

merge_bam_by_header_prepare_gfu =
{
    doc title: "Prepare Merge bam files with $PICMERGE using bam headers SM",
        desc: "",
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    produce("merge_rule.txt")
    {
        exec """
            for i in ${inputs.bam};
            do SAMPLE=`$SAMTOOLS view -H \$i | grep SM | sed -e 's/.*SM://' | sed -e 's/CN:.*//'`;
            echo -e "\$i\t\$SAMPLE";
            done > merge_rule.txt
        """
    }
}
