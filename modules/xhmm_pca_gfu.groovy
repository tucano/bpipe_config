// MODULE XHMM PCA

@intermediate
xhmm_pca_gfu =
{
    var pretend : false

    doc title: "XHMM PCA",
        desc: """
            CONFIGURATION:
            pretend : $pretend
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    def outputs = [
        "DATA.RD_PCA.PC_LOADINGS.txt",
        "DATA.RD_PCA.PC_SD.txt",
        "DATA.RD_PCA.PC.txt"
    ]

    requires XHMM, "Please define the XHMM path"

    produce(outputs)
    {
        def command = """
            $XHMM --PCA -r ./DATA.filtered_centered.RD.txt --PCAfiles ./DATA.RD_PCA
        """

        if (pretend)
        {
            println """
                INPUT: $inputs
                OUTPUT: $outputs
                COMMAND: $command
            """
            command = "touch ${outputs.join(" ")}"
        }

        exec "$command","xhmm"
    }
}
