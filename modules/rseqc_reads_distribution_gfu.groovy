// MODULE READS DISTRIBUTIONS FROM RSEQC (rev1)

@preserve
rseqc_reads_distribution_gfu =
{
    var pretend : false

    doc title: "Rseqc quality control of bam files: reads_distribution",
        desc: """
           Provided a BAM/SAM file and reference gene model, this module will calculate
           how mapped reads were distributed over genome feature
           (like CDS exon, 5’UTR exon, 3’ UTR exon, Intron, Intergenic regions).
        """,
        constrains: "I am forcing export of site-packages to get qcmodule",
        author: "davide.rambaldi@gmail.com"

    def required_binds = ["READS_DISTRIBUTION","BED12_ANNOTATION"]
    def to_fail = false
    required_binds.each { key ->
        if (!binding.variables.containsKey(key))
        {
            to_fail = true
            println """
                This stage require this variable: $key, add this to the groovy file:
                    $key = "VALUE"
            """.stripIndent()
        }
    }
    if (to_fail) { System.exit(1) }

    transform("reads_distribution.log")
    {
        def command = """
            $READS_DISTRIBUTION -i $input.bam -r $BED12_ANNOTATION 1> $output
        """
        if (pretend)
        {
            println """
                INPUT $input
                OUTPUTS: $output
                COMMAND: $command
            """

            command = """
                echo "INPUT: $input" > $output
            """
        }
        exec command
    }
    forward input.bam
}
