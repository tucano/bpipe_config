// MODULE GENERATE TRUSEQ INTERVALS
@intermediate
generate_truseq_intervals_gfu = {
    var test : false

    doc title: "Generate per chromosome intervals from TruSeq file",
        desc: "I use a per chromosome for intervals to reduce granularity",
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    produce ("${chr}.intervals") {
        def command = """
            grep "${chr}:" $TRUSEQ > $output
        """
        if (test) {
            println "INPUT $inputs, OUTPUT: $output, CHROMOSOME: $chr"
            println "COMMAND: $command"
            command = "touch $output"
        }
        exec command
    }
}
