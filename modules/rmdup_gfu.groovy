// MODULE SAMTOOLS RMDUP
SAMTOOLS="/usr/local/cluster/bin/samtools"

@preserve
rmdup_gfu = 
{
    var pretend   : false
    var paired    : true

    doc title: "Remove duplicates in bam file with SAMTOOLS rmdup",
        desc: "Remove duplicates in bam file with samtools. This is an alternative to Picard MarkDuplicates",
        constrains: "Remove duplicates (can't mark)",
        author: "davide.rambaldi@gmail.com"

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
