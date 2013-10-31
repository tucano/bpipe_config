// MODULE SORT BAM BY NAME FILE GFU
SAMTOOLS="/usr/local/cluster/bin/samtools"

@intermediate
sort_bam_by_name_gfu =
{
    var test : false
    doc title: "Samtools: sort by name bam file",
        desc: "Sort bam file by name",
        constrains: "...",
        author: "davide.rambaldi@gmail.com"

    filter("sorted_by_name") {
        def command = """
            $SAMTOOLS sort -n $input.bam $output.prefix
        """.stripIndent()

        if (test) {
            println "INPUT $input, OUTPUT: $output OUTPUT PREFIX: $output.prefix"
            println "COMMAND: $command"
            command = "touch $output"
        }
        exec command
    }
}
