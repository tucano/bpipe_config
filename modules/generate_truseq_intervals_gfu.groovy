// MODULE GENERATE TRUSEQ INTERVALS (rev1)

@intermediate
generate_truseq_intervals_gfu =
{
    // stage vars
    var pretend : false

    doc title: "Generate per chromosome intervals from Intervals file",
        desc: """
            This stage subdivide the intervals in the file $INTERVALS per chromosome.
            Per chromosome intervals files will be used as input for GATK UnifiedGenotyper.
            Main options with value:
                pretend   : $pretend
                INTERVALS : $INTERVALS
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    def required_binds = ["INTERVALS"]
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

    produce ("${chr}.intervals")
    {
        def command = """
            grep "${chr}:" $INTERVALS > $output;
        """

        if (pretend)
        {
            println """
                INPUT $inputs
                OUTPUT: $output
                CHROMOSOME: $chr
                COMMAND: $command
            """

            command = """
                echo "INPUTS: $inputs" > $output
            """
        }
        exec command
    }
}
