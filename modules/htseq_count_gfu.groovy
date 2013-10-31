// MODULE HTSEQ COUNT GFU
HTSEQ_COUNT="/usr/local/cluster/python2.7/bin/python2.7 /usr/local/cluster/python2.7/bin/htseq-count"
SAMTOOLS="/usr/local/cluster/bin/samtools"

@preserve
htseq_count_gfu =
{
    var test         : false
    var stranded     : "no"
    var mode         : "union"
    var id_attribute : "gene_name"
    var feature_type : "exon"

    doc title: "GFU: htseq-count on bam file with GTF annotation file",
        desc: """
            Run htseq-count on sorted BAM file and check output consistency with awk in file $output.txt
            Inputs: a gtf annotation file ($GENE_GFF_FILE) and a bam file ($input.bam).
            Outputs: sam files of reads ($output.sam) and reads count ($output.txt).
        """,
        constraints: """
            Generate a SAM file without Headers.
            Check the var id_attribute, feature_type, mode and stranded before launch.
            This stage forward the last bam file to create headers in sort_and_convert_sam_gfu
        """,
        author: "davide.rambaldi@gmail.com"

    transform("reads.txt","reads.sam") {
        def command = """
            $SAMTOOLS view $input | $HTSEQ_COUNT -m $mode -s $stranded -i $id_attribute -o $output.sam - $ANNOTATION_GFF_FILE > $output.txt;
            test \$(awk '{sum += \$2} END {print sum}' $output.txt) -gt 0;
        """
        if (test) {
            println "INPUT $input, OUTPUTS: $output.sam $output.txt"
            println "COMMAND: $command"
            command = "touch $output.sam $output.txt"
        }
        exec command, "htseq_count"
    }
    forward input.bam
}
