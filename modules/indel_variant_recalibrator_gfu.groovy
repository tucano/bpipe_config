// MODULE INDEL VARIANT RECALIBRATOR

@intermediate
indel_variant_recalibrator_gfu =
{

    var pretend         : false
    var unsafe          : "ALLOW_SEQ_DICT_INCOMPATIBILITY"
    var max_gaussian    : 4
    var percent_bad     : 0.01
    var min_num_bad     : 1000
    var inbreeding_coef : false  // FOR MORE THAN 10 SAMPLES

    // INFO
    doc title: "GATK VariantRecalibration",
        desc: """
            Create a Gaussian mixture model by looking at the annotations values
            over a high quality subset of the input call set and then evaluate all input variants.
            Outputs:
                A recalibration table file in VCF format that is used by the ApplyRecalibration walker.
                A tranches file which shows various metrics of the recalibration callset as a function
                of making several slices through the data.
                An R plot script.

            stage options with value:
                pretend          : $pretend
                unsafe           : $unsafe
                max_gaussian     : $max_gaussian
                percent_bad      : $percent_bad
                min_num_bad      : $min_num_bad
                inbreeding_coef  : $inbreeding_coef
        """,
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"

    requires REFERENCE_GENOME_FASTA: "Please define a REFERENCE_GENOME_FASTA"
    requires GATK: "Please define GATK path"

    transform("indel.recal.csv","indel.tranches","indel.plot.R")
    {
        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -T VariantRecalibrator
                  -R $REFERENCE_GENOME_FASTA
                  -input $input.vcf
                  --maxGaussians $max_gaussian
                  -percentBad $percent_bad
                  -minNumBad $min_num_bad
                  -resource:mills,VCF,known=true,training=true,truth=true,prior=12.0 $MILLIS
                  -resource:dbsnp,VCF,known=true,training=false,truth=false,prior=2.0 $DBSNP
                  -an FS
                  -an ReadPosRankSum
                  -an MQRankSum
                  -mode INDEL
                  -recalFile $output1
                  -tranchesFile $output2
                  -rscriptFile $output3
                  -U $unsafe ${ inbreeding_coef ? "-an InbreedingCoeff" : ""};
        """

        if (pretend)
        {
            println """
                INPUT: $input.vcf
                OUTPUTS: $output1, $output2, $output3
                COMMAND: $command
            """
            command = """
                echo "INPUT: $input" > $output1;
                echo "INPUT: $input" > $output2;
                echo "INPUT: $input" > $output3;
            """
        }

        exec command, "gatk"
    }
    forward input.vcf
}

