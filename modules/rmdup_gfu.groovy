// MODULE SAMTOOLS RMDUP

@preserve
rmdup_gfu =
{
    var pretend    : false
    var paired     : true
    var sample_dir : false

    doc title: "Remove duplicates in bam file with SAMTOOLS rmdup",
        desc: """
            Remove duplicates in bam file with samtools. This is an alternative to Picard MarkDuplicates.
            stage options:
                pretend               : $pretend
                paired                : $paired
                sample_dir            : $sample_dir
            With sample_dir true, this stage redefine output.dir using input.dir
        """,
        constrains: "Remove duplicates (can't mark)",
        author: "davide.rambaldi@gmail.com"

    if (sample_dir) { output.dir = input.replaceFirst("/.*","") }

    requires SAMTOOLS: "Please define SAMTOOLS path"

    filter("rmdup")
    {
        def command
        if (paired)
        {
            command = "$SAMTOOLS rmdup $input.bam $output.bam"
        }
        else
        {
            command = "$SAMTOOLS rmdup -s $input.bam $output.bam"
        }

        if (pretend) {
            println """
                INPUTS: $inputs
                OUTPUT: $output
                COMMAND: $command
            """
            command = """
                echo "INPUTS: $inputs" > $output
            """
        }
        exec command, "samtools"
    }
}
