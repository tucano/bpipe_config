// MODULE VERIFY BAM

verify_bam_gfu =
{
    var pretend : false

    doc title: "Verify BAM file",
        desc: "Use a function to verify BAM file using the EOF (copied by Davide Cittaro bashrc)",
        constraints: "Ask to Davide Cittaro",
        author: "davide.rambaldi@gmail.com"

    BGZF_EOF="1F8B08040000000000FF0600424302001B00030000000000*"


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
