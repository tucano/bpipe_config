// MODULE VERIFY BAM (rev1)

verify_bam_gfu =
{
    var pretend    : false
    var sample_dir : false

    doc title: "Verify BAM file",
        desc: """
            Use a function to verify BAM file using the EOF (copied by Davide Cittaro bashrc)
             stage options:
                pretend    : $pretend
                sample_dir : $sample_dir
            With sample_dir true, this stage redefine output.dir using input.dir
        """,
        constraints: "...",
        author: "davide.rambaldi@gmail.com"

    BGZF_EOF="1F8B08040000000000FF0600424302001B00030000000000*"

    if (sample_dir) { output.dir = branch.sample }

    def command = """
        size=\$(stat -c "%s" $input);
        seek=\$(( $size - 28 ));
        BAM_EOF=`hexdump -e  '4/1 "%02X"' -s $seek $input`;
        test $BAM_EOF == $BGZF_EOF
    """

    if (pretend)
    {
        println "INPUT $input"
        println "COMMAND: $command"
        command = "true"
    }

    exec command
    forward input
}
