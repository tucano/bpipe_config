// MODULE UNIFIED INDEL APPLY RECALIBRATION
@intermediate
indel_apply_recalibration_gfu =
{
    var test      : false
    var unsafe    : "ALLOW_SEQ_DICT_INCOMPATIBILITY"

    // INFO
    doc title: "GFU: INDEL Apply recalibration",
        desc: " ... ",
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"

    filter("indel_recalibrated") {
        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -T ApplyRecalibration
                  -R $REFERENCE_GENOME_FASTA
                  -input $input.vcf
                  -tranchesFile $input.tranches
                  -recalFile $input.csv
                  --mode INDEL
                  -o $output
                  -U $unsafe
        """

        if (test) {
            println "INPUT: $input.vcf $input.tranches $input.csv OUTPUT: $output"
            println "COMMAND: $command"
            command = "touch $output"
        }

        exec command, "gatk"
    }
}
