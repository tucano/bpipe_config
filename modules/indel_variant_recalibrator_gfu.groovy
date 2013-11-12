// MODULE UNIFIED INDEL VARIANT RECALIBRATOR
@intermediate
indel_variant_recalibrator_gfu =
{

    var test            : false
    var unsafe          : "ALLOW_SEQ_DICT_INCOMPATIBILITY"
    var max_gaussian    : 4
    var percent_bad     : 0.01
    var min_num_bad     : 1000
    var inbreeding_coef : false  // FOR MORE THAN 10 SAMPLES

    // INFO
    doc title: "GFU: INDEL Variants recalibration",
        desc: " ... ",
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"

    // we transform a VCF into a set of files
    transform("indel.recal.csv","indel.tranches","indel.plot.R") {
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

        if (test) {
            println "INPUT: $input.vcf OUTPUTS: $output1, $output2, $output3"
            println "COMMAND: $command"
            command = "touch $output1 $output2 $output3"
        }

        exec command, "gatk"
    }
    forward input.vcf
}

