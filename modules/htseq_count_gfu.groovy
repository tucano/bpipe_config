// MODULE HTSEQ COUNT GFU
HTSEQ_COUNT="/usr/local/cluster/python2.7/bin/python2.7 /usr/local/cluster/python2.7/bin/htseq-count"
SAMTOOLS="/usr/local/cluster/bin/samtools"

@preserve
htseq_count_gfu =
{
    var pretend         : false
    var stranded     : "no"
    var mode         : "union"
    var id_attribute : "gene_name"
    var feature_type : "exon"

    doc title: "Htseq-count on bam file with GTF annotation file",
        desc: """
            Run htseq-count on a sorted BAM file and check output consistency with awk in output file ${output.txt}.
            Inputs: a gtf annotation file (${ANNOTATION_GFF_FILE}) and a bam file (${input.bam}).
            Outputs: sam files of reads ($output.sam) and reads count (${output.txt}).

            htseq_count options with value:
            pretend          : $pretend
            stranded         : $stranded
            mode             : $mode
            id_attribute     : $id_attribute
            feature_type     : $feature_type
        """,
        constraints: """
            Generate a SAM file without Headers.
            Check the var id_attribute, feature_type, mode and stranded before launch.
            This stage forward the last bam file to create headers in sort_and_convert_sam_gfu
        """,
        author: "davide.rambaldi@gmail.com"

    transform("reads.txt","reads.sam") 
    {
        
        def command = """
            export LD_LIBRARY_PATH=\$LD_LIBRARY_PATH/usr/local/cluster/python2.7/lib/;
            $SAMTOOLS view $input | $HTSEQ_COUNT -m $mode -s $stranded -i $id_attribute -o $output.sam - $ANNOTATION_GFF_FILE > $output.txt;
            test \$(awk '{sum += \$2} END {print sum}' $output.txt) -gt 0;
        """

        if (pretend) 
        {
            println """
                INPUT $input
                OUTPUTS: $output.sam $output.txt
                COMMAND: $command
            """
            command = """
                echo "INPUT: $input" > $output.sam;
                echo "INPUT: $input" > $output.txt;
            """
        }
        exec command, "htseq_count"
    }
    forward input.bam
}
