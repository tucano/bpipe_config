// MODULE XHMM FILTER AND ZSCORE

xhmm_filter_and_zscore_gfu =
{
    var pretend : false
    var center_type : "sample"
    var max_sd_target_rd : 30

    doc title: "XHMM FILTER AND ZSCORE",
        desc: """
            CONFIGURATION:
            pretend : $pretend
            center_type : $center_type
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    def outputs = [
        "DATA.PCA_normalized.filtered.sample_zscores.RD.txt",
        "DATA.PCA_normalized.filtered.sample_zscores.RD.txt.filtered_targets.txt",
        "DATA.PCA_normalized.filtered.sample_zscores.RD.txt.filtered_samples.txt"
    ]

    def required_binds = ["XHMM"]
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

    produce(outputs)
    {
        def command = """
            $XHMM --matrix -r ./DATA.PCA_normalized.txt --centerData --centerType $center_type --zScoreData
            -o ./DATA.PCA_normalized.filtered.sample_zscores.RD.txt
            --outputExcludedTargets ./DATA.PCA_normalized.filtered.sample_zscores.RD.txt.filtered_targets.txt
            --outputExcludedSamples ./DATA.PCA_normalized.filtered.sample_zscores.RD.txt.filtered_samples.txt
            --maxSdTargetRD $max_sd_target_rd
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

        exec command, "xhmm"
    }
}
