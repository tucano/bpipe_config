// MODULE SAMTOOLS RMDUP
SAMTOOLS="/usr/local/cluster/bin/samtools"

@preserve
rmdup_gfu = {
    var test   : false
    var paired : true

    doc title: "Remove duplicates in bam files with SAMTOOLS rmdup",
        desc: "REmove duplicates in bam files with samtools. This is an alternative to Picard MarkDuplicates",
        constrains: "Remove duplicates (can't mark)",
        author: "davide.rambaldi@gmail.com"

    def output_prefix = input.prefix.replaceFirst(/.*\//,"").replaceFirst(/_R.*/,"")
    def output_bam    = output_prefix + ".dedup.bam"

    from("$input") produce(output_bam) {
        def command
        if (paired) {
            command = "$SAMTOOLS rmdup $input.bam $output.bam"
        } else {
            command = "$SAMTOOLS rmdup -s $input.bam $output.bam"
        }
        if (test) {
            println "INPUT: $input OUTPUT: $output"
            println "COMMAND: $command"
            command = "touch $output"
        }
        exec command, "samtools"
    }
}
