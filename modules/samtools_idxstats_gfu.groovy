// MODULE GENE COVERAGE FROM RSEQC
SAMTOOLS="/usr/local/cluster/bin/samtools"

@preserve
samtools_idxstats_gfu =
{
    var test : false

    doc title: "GFU: samtools idxstats on bam file (index)",
        desc: """
            Retrieve and print stats in the index file.
            The output is TAB delimited with each line consisting of reference sequence name,
            sequence length, # mapped reads and # unmapped reads.
        """,
        constrains: "BAM file $input.bam must be indexed",
        author: "davide.rambaldi@gmail.com"

    transform("idxstats.log") {
        def command = """
            $SAMTOOLS idxstats $input.bam > $output
        """
        if (test) {
            println "INPUT $input, OUTPUT: $output"
            println "COMMAND: $command"
            command = "touch $output"
        }
        exec command
    }
    forward input.bam
}
