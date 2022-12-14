// MODULE XHMM DISCOVER CNV (rev1)

xhmm_discover_cnv_gfu =
{
    var pretend : false
    var params  : "1e-08,6,70,-3,1,0,1,3,1"

    doc title: "XHMM: discover cnv",
        desc: """
            CONFIGURATION:
            pretend : $pretend
            params  : $params
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    requires XHMM : "Please define the XHMM path"

    // GENERATE PARAMS file from string
    new File("params.txt").write(params.split(",").join("\t"))

    def outputs = [
        "DATA.xcnv",
        "DATA.aux_xcnv"
    ]

    produce(outputs)
    {
        def command = """
            $XHMM --discover -p params.txt -r ./DATA.PCA_normalized.filtered.sample_zscores.RD.txt -R ./DATA.same_filtered.RD.txt
            -c ./DATA.xcnv -a ./DATA.aux_xcnv -s ./DATA
        """

        if (pretend)
        {
            println """
                INPUTS: $inputs
                OUTPUTS: $outputs
                COMMAND: $command
            """
            command = "touch ${outputs.join(" ")}"
        }
        exec "$command", "xhmm"
    }
}
