// MODULE xhmm_merge_depth_of_coverage_gfu (rev1)

xhmm_merge_depth_of_coverage_gfu =
{
    var pretend             : false
    var use_external_depths : true

    doc title: "XHMM: Merge DepthOfCoverage",
        desc: """
            CONFIGURATION:
            pretend : $pretend
            use_external_depths : $use_external_depths
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    def required_binds = ["DEPTH_DATA_DIR","XHMM"]
    def to_fail = false
    required_binds.each { key ->
        if (!binding.variables.containsKey(key))
        {
            to_fail = true
            println """
                This stage require this variable: $key, add this to the groovy file:
                    $key = "VALUE"
            """.stripIndent()
        }
    }
    if (to_fail) { System.exit(1) }

    def input_string = new StringBuffer()
    inputs.each { file ->
        input_string << "--GATKdepths $file "
    }

    if (use_external_depths) {
        new File("$DEPTH_DATA_DIR").eachFileMatch(~/.*\.sample_interval_summary/) { file ->
            input_string << "--GATKdepths $file "
        }
    }

    produce("DATA.RD.txt")
    {
        def command = """
            $XHMM --mergeGATKdepths -o ./DATA.RD.txt $input_string
        """

        if (pretend)
        {
            println """
                INPUT:   $inputs
                OUTPUT:  $output
                COMMAND: $command
            """
            command = "touch $output"
        }
        exec "$command","xhmm"
    }
}
