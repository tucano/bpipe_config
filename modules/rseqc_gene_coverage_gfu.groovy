// MODULE GENE COVERAGE FROM RSEQC

@preserve
rseqc_gene_coverage_gfu =
{
    var pretend : false

    doc title: "Rseqc quality control of bam files: gene_coverage",
        desc: """
            Read coverage over gene body.
            This module is used to check if reads coverage is uniform and if there is any 5’/3’ bias.
            This module scales all transcripts to 100 nt and calculates the number of reads covering
            each nucleotide position. Finally, it generates a plot illustrating the coverage profile
            along the gene body.

            stage options:
                pretend               : $pretend
        """,
        constrains: "I am forcing export of site-packages to get qcmodule",
        author: "davide.rambaldi@gmail.com"

    transform("geneBodyCoverage.pdf","geneBodyCoverage_plot.r","geneBodyCoverage.txt")
    {
        def command = """
            $GENECOVERAGE -r $BED12_ANNOTATION -i $input.bam  -o $input.prefix
        """

        if (pretend)
        {
            println """
                INPUT: $input
                OUTPUTS: $output1 $output2 $output3
                COMMAND: $command
            """

            command = """
                echo "INPUT: $input" > $output1;
                echo "INPUT: $input" > $output2;
                echo "INPUT: $input" > $output3;
            """
        }
        exec command
    }
    forward input.bam
}
