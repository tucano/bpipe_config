// MODULE BAM STAT FROM RSEQC (rev1)

@preserve
rseqc_bam_stat_gfu =
{
    var pretend : false

    doc title: "Rseqc quality control of bam files: bam_stat",
        desc: """
            This program is used to calculate reads mapping statistics from provided BAM file.
            This script determines “uniquely mapped reads” from mapping quality, which quality
            the probability that a read is misplaced (Do NOT confused with sequence quality,
            sequence quality measures the probability that a base-calling was wrong).

            stage options:
                pretend               : $pretend
        """,
        constrains: "I am forcing export of site-packages to get qcmodule",
        author: "davide.rambaldi@gmail.com"

    def required_binds = ["BAMSTAT"]
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

    transform("bam_stat.log")
    {
        def command = """
            $BAMSTAT -i $input.bam 2> $output;
        """

        if (pretend)
        {
            println """
                INPUT $input
                OUTPUT: $output
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
