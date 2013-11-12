// MODULE UNIFIED SNP APPLY RECALIBRATOR
@intermediate
snp_apply_recalibration_gfu =
{
    var test      : false
    var unsafe    : "ALLOW_SEQ_DICT_INCOMPATIBILITY"

    // INFO
    doc title: "GFU: SNP Apply recalibration",
        desc: " ... ",
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"

    filter("snp_recalibrated") {
        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -T ApplyRecalibration
                  -R $REFERENCE_GENOME_FASTA
                  -input $input.vcf
                  -tranchesFile $input.tranches
                  -recalFile $input.csv
                  --mode SNP
                  -o $output
                  -U $unsafe
        """

        if (test) {
            println "INPUT: $input.vcf OUTPUTS: $output1, $output2, $output3"
            println "COMMAND: $command"
            command = "touch $output"
        }

        exec command, "gatk"
    }
}
