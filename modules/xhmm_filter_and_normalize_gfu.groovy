// MODULE XHMM FILTER AND NORMALIZE

xhmm_filter_and_normalize_gfu =
{
    var pretend : false
    var pve_mean_fac : 0.7

    doc title: "XHMM FILTER AND NORMALIZE",
        desc: """
            CONFIGURATION:
            pretend : $pretend
            pve_mean_fac: $pve_mean_fac
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    requires XHMM, "Please define the XHMM path"

    produce("DATA.PCA_normalized.txt")
    {
        def command = """
            $XHMM --normalize -r ./DATA.filtered_centered.RD.txt --PCAfiles ./DATA.RD_PCA
            --normalizeOutput ./DATA.PCA_normalized.txt --PCnormalizeMethod PVE_mean --PVE_mean_factor $pve_mean_fac
        """

        if (pretend)
        {
            println """
                INPUT: $inputs
                OUTPUT: $outputs
                COMMAND: $command
            """
            command = "touch $output"
        }
        exec "$command", "xhmm"
    }
}
