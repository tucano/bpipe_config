// MODULE SAMTOOLS CREATE BAI FILE
SAMTOOLS="/usr/local/cluster/bin/samtools"

@preserve
samtools_index_gfu =
{
    var test : false

    doc title: "Create BAi file from BAM file",
        desc: "Launch $SAMTOOLS index on $input.bam. Create link from bam.bai to .bai",
        constraints: "Generate a LOG file and forward input to next stage",
        author: "davide.rambaldi@gmail.com"

    transform("bai") {
        def command = """
            $SAMTOOLS index $input.bam;
            ln -s ${input}.bai $output;
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
