// MODULE UNIFIED SNP APPLY RECALIBRATOR
@intermediate
snp_apply_recalibration_gfu =
{
    var test      : false
    var unsafe    : "ALLOW_SEQ_DICT_INCOMPATIBILITY"

    // INFO
    doc title: "SNP Apply recalibration",
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
                  -o $output.csv
                  -U $unsafe
        """

        if (test) {
            println "INPUTS: $input.vcf $input.tranches $input.csv OUTPUTS: $output"
            println "COMMAND: $command"
            command = "touch $output.csv"
        }

        exec command, "gatk"
    }
}
