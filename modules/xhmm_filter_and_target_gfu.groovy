// MODULE XHMM FILTER AND TARGET

xhmm_filter_and_target_gfu =
{
    var pretend : false
    var center_type : "target"
    var minMeanTargetRD : 10
    var maxMeanTargetRD : 500

    doc title: "XHMM: Merge filter and target",
        desc: """
            CONFIGURATION:
            pretend     : $pretend
            centerType  : $center_type
            minMeanTargetRD : $minMeanTargetRD
            maxMeanTargetRD : $maxMeanTargetRD
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    def outputs = [
        "DATA.filtered_centered.RD.txt",
        "DATA.filtered_centered.RD.txt.filtered_targets.txt",
        "DATA.filtered_centered.RD.txt.filtered_samples.txt"
    ]

    produce(outputs)
    {
        def command = """
            $XHMM --matrix -r DATA.RD.txt --centerData --centerType $center_type -o ./DATA.filtered_centered.RD.txt
            --outputExcludedTargets ./DATA.filtered_centered.RD.txt.filtered_targets.txt
            --outputExcludedSamples ./DATA.filtered_centered.RD.txt.filtered_samples.txt
            --minMeanTargetRD $minMeanTargetRD --maxMeanTargetRD $maxMeanTargetRD
        """

        if (pretend)
        {
            println """
                INPUTS:  $inputs
                OUTPUTS: $outputs
                COMMAND: $command
            """.stripIndent()
            command = "touch ${outputs.join(" ")}"
        }

        exec "$command","xhmm"
    }
}
