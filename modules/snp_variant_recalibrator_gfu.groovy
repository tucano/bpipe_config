// MODULE UNIFIED SNP VARIANT RECALIBRATOR

@intermediate
snp_variant_recalibrator_gfu =
{

    var pretend         : false
    var inbreeding_coef : false  // FOR MORE THAN 10 SAMPLES
    var unsafe          : "ALLOW_SEQ_DICT_INCOMPATIBILITY"
    var max_gaussian    : 4
    var percent_bad     : 0.01
    var min_num_bad     : 1000

    // INFO
    doc title: "SNP Variants recalibration",
        desc: "Create a Gaussian mixture model by looking at the annotations values over a high quality subset of the input call set and then evaluate all input variants.",
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"

    // we transform a VCF into a set of files
    transform("snp.recal.csv","snp.tranches","snp.plot.R")
    {
        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -T VariantRecalibrator
                  -R $REFERENCE_GENOME_FASTA
                  -I $input.vcf
                  --maxGaussians $max_gaussian
                  -percentBad $percent_bad
                  -minNumBad $min_num_bad
                  -resource:hapmap,VCF,known=false,training=true,truth=true,prior=15.0 $HAPMAP
                  -resource:1000G,VCF,known=false,training=true,truth=false,prior=10.0 $ONETH_GENOMES
                  -resource:omni,VCF,known=false,training=true,truth=false,prior=12.0 $ONEKG_OMNI
                  -resource:dbsnp,VCF,known=true,training=false,truth=false,prior=2.0 $DBSNP
                  -an QD
                  -an HaplotypeScore
                  -an MQRankSum
                  -an ReadPosRankSum
                  -an FS
                  -mode SNP
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
                echo "INPUTS: $inputs" > $output1;
                echo "INPUTS: $inputs" > $output2;
                echo "INPUTS: $inputs" > $output3;
            """
        }

        exec command,"gatk"
    }
    forward input.vcf
}

