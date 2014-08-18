// MODULE CONFIG LUSTRE FS (rev1)

@intermediate
set_stripe_gfu =
{
    var pretend : false

    doc title: "Set lustre options for working directory",
        desc: """
            Lustre options:
                -c -1 : a stripe_count of -1 means to stripe over all available OSTs.
                -i -1 : a start_ost_index of -1 allows the MDS to choose the starting index and it is strongly recommended, as this allows space and load balancing to be done by the MDS as needed.
                -s 2M : Stripsize 2 megabytes
        """,
        constraints: "It is a non blocking stage (Fails in non lustre fs, but will return always true). Forward input to next stage.",
        author: "davide.rambaldi@gmail.com"

    def cwd = System.getProperty("user.dir")

    requires LSF : "Please define path of LSF"

    produce("setstripe.log")
    {
        def command = """
            $LSF setstripe -c -1 -i -1 -s 2M $cwd 1>/dev/null 2>&1 || true;
            $LSF getstripe -d $cwd 1> $output 2>&1 || true;
        """

        if (pretend)
        {
            println """
                COMMAND: $command
                DIR: $cwd
            """
            command = "touch $output"
        }
        exec command
    }
}
