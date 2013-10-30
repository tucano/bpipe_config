// MODULE MERGE JUNC FILES

@preserve
merge_junc_gfu =
{
    var test : false

    doc title: "GFU: merge junc files",
        desc: "Merge junc files",
        constraints: "Should be placed after merge_bam_gfu in order to forward $input.bam to the next stage",
        author: "davide.rambaldi@gmail.com"

    transform("junc") {
        def command = """
            echo -e "[merge_junc_gfu]: Merging junc files in $output" >&2;
            JUNC=\$(ls *.junc 2>/dev/null);
            touch $output;
            for F in $JUNC; do
                if [[ -e $F ]]; then
                    cat $F >> $output;
                fi;
            done;
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
