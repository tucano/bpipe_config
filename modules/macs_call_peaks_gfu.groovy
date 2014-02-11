// MODULE MACS CALL PEAKS GFU
MACS="/usr/local/cluster/python2.7/bin/macs2"

@intermediate
macs_call_peaks_gfu =
{
    var pretend : false
    var format  : "BAM"

    doc title: "Rmacs2 callpeaks",
        desc: """
            stage options with value:
                pretend          : $pretend
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

    produce(outputs)
    {
        def command = """
            $MACS callpeak -t $input.bam -n ${input.prefix} -f $format -g $GSIZE --keep-dup 1 --broad
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
