// MODULE XHMM FILTER ORIGINAL DATA

xhmm_filter_original_data_gfu =
{
    var pretend : false

    doc title: "XHMM FILTER ORIGINAL DATA",
        desc: """
            CONFIGURATION:
            pretend : $pretend
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    requires XHMM : "Please define the XHMM path"

    produce("DATA.same_filtered.RD.txt")
    {
        def command = """
            $XHMM --matrix -r ./DATA.RD.txt --excludeTargets ./DATA.filtered_centered.RD.txt.filtered_targets.txt
            --excludeTargets ./DATA.PCA_normalized.filtered.sample_zscores.RD.txt.filtered_targets.txt
            --excludeSamples ./DATA.filtered_centered.RD.txt.filtered_samples.txt
            --excludeSamples ./DATA.PCA_normalized.filtered.sample_zscores.RD.txt.filtered_samples.txt
            -o ./DATA.same_filtered.RD.txt
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
