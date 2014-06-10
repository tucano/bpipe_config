// MODULE MACS CALL PEAKS GFU (rev1)

@intermediate
macs_call_peaks_gfu =
{
    var pretend  : false
    var format   : "AUTO"
    var keep_dup : "1"
    var broad    : true

    doc title: "Macs2 callpeaks",
        desc: """
            stage options with value:
                pretend  : $pretend
                format   : $format
                keep_dup : $keep_dup
                broad    : $broad
        """,
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"

    def outputs = [
        "${input.prefix}_peaks.bed",
        "${input.prefix}_peaks.narrowPeak",
        "${input.prefix}_peaks.xls",
        "${input.prefix}_summits.bed",
        "${input.prefix}_model.r",
        "${input.prefix}_broad_peaks.bed"
    ]

    def required_binds = ["MACS","GSIZE"]
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

    produce(outputs)
    {
        def command = """
            $MACS callpeak -t $input.bam -n ${input.prefix} -f $format -g $GSIZE --keep-dup $keep_dup ${ broad ? "--broad" : "" }
        """

        if (pretend)
        {
            println """
                INPUT $input
                OUTPUT: $output
                COMMAND: $command
            """

            command = """
                echo "INPUT: $input" > $output1;
                echo "INPUT: $input" > $output2;
                echo "INPUT: $input" > $output3;
                echo "INPUT: $input" > $output4;
                echo "INPUT: $input" > $output5;
                echo "INPUT: $input" > $output6;
            """
        }
        exec command, "macs"
    }
}
