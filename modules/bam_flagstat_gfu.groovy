// MODULE FLAGSTAT ON BAM
SAMTOOLS="/usr/local/cluster/bin/samtools"

@preserve
bam_flagstat_gfu =
{
    var test : false

    doc title: "GFU: falgstat on BAM file",
        desc: "Launch $SAMTOOLS flagstat on the final (merged) bam file, produce a log file $output",
        constraints: "Generate a LOG file and forward input to next stage",
        author: "davide.rambaldi@gmail.com"

    transform("log") {
        def command = """
            echo -e "[bam_flagstat_gfu]: flagstat with input $input.bam and output file $output" >&2;
            $SAMTOOLS flagstat $input.bam > $output;
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
