// MODULE GENE READ GC RSEQC
READGC = """
    export PYTHONPATH=/usr/local/cluster/python/usr/lib64/python2.6/site-packages/:\$PYTHONPATH &&
    /usr/local/cluster/python2.7/bin/python2.7 /usr/local/cluster/python/usr/bin/read_GC.py
""".stripIndent().trim()

@preserve
rseqc_read_GC_gfu =
{
    var pretend : false

    doc title: "Rseqc quality control of bam files: read_GC",
        desc: """
            Read GC content.
            stage options:
                pretend               : $pretend
        """,
        constrains: "I am forcing export of site-packages to get qcmodule",
        author: "davide.rambaldi@gmail.com"

    transform("GC_plot.r","GC_plot.pdf","GC.xls")
    {
        def command = """
            $READGC -i $input.bam -o $input.prefix;
        """

        if (pretend)
        {
            println """
                INPUT:   $input
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
