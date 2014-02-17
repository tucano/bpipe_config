// MODULE UNIFIED SNP APPLY RECALIBRATOR
@intermediate
snp_apply_recalibration_gfu =
{
    var pretend      : false
    var unsafe       : "ALLOW_SEQ_DICT_INCOMPATIBILITY"

    // INFO
    doc title: "SNP Apply recalibration",
        desc: "Applies cuts to the input vcf file (by adding filter lines) to achieve the desired novel truth sensitivity levels which were specified during VariantRecalibration",
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"

    filter("snp_recalibrated")
    {
        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -T ApplyRecalibration
                  -R $REFERENCE_GENOME_FASTA
                  -input $input.vcf
                  -tranchesFile $input.tranches
                  -recalFile $input.csv
                  --mode SNP
                  -o $output.vcf
                  -U $unsafe
        """

        if (pretend)
        {
            println """
                INPUTS: $input.vcf $input.tranches $input.csv
                OUTPUTS: $output.vcf
                COMMAND: $command
            """

            command = """
                echo "INPUTS: $inputs" > $output.vcf
            """
        }

        exec command, "gatk"
    }
}
