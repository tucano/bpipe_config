// MODULE MERGE JUNC FILES (rev1)

@preserve
merge_junc_gfu =
{
    var pretend : false

    doc title: "Merge junc files",
        desc: "Merge junc files",
        constraints: "Should be placed after merge_bam_gfu in order to forward $input.bam to the next stage",
        author: "davide.rambaldi@gmail.com"

    transform("junc")
    {
        def command = """
            JUNC=\$(ls *.junc 2>/dev/null);
            touch $output;
            for F in $JUNC; do
                if [[ -e $F ]]; then
                    cat $F >> $output;
                fi;
            done;
        """

        if (pretend)
        {
            println """
                INPUT $input
                OUTPUT: $output
                COMMAND: $command
            """
            command = "touch $output"
        }
        exec command
    }
    forward input.bam
}
