// MODULE UNIFIED GENOTYPER BY CHROMOSOME GFU
GATK = "java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"

@intermediate
unified_genotyper_by_chromosome_gfu =
{
    var pretend      : false
    var call_conf : 20.0
    var nct       : 16
    var glm       : "BOTH"
    var unsafe    : "ALLOW_SEQ_DICT_INCOMPATIBILITY"

    doc title: "GATK: Unified Genotyper",
        desc: "Produce a VCF file with SNP calls and INDELs. Parallelized in 1 job for chromosome",
        author: "davide.rambaldi@gmail.com"

    transform("vcf")
    {
        def configuration = """
            Inputs: ${inputs}
            Output: ${output.vcf}
            CONFIGURATION:
                Reference       = $REFERENCE_GENOME_FASTA
                Inputs          = $inputs.bam
                DBSNP           = $DBSNP
                nct             = $nct
                stand_call_conf = $call_conf
                glm             = $glm
                Output          = ${output.vcf}
                region          = $chr
                Unsafe          = $unsafe
        """.stripIndent()

         def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -R $REFERENCE_GENOME_FASTA
                  ${inputs.bam.collect{ "-I $it" }.join(" ")}
                  --dbsnp $DBSNP
                  -T UnifiedGenotyper
                  -nct $nct
                  -stand_call_conf $call_conf
                  -glm $glm
                  -U $unsafe
                  -L $chr
                  -o $output.vcf
        """

        if (pretend)
        {
            println "INPUT: $inputs"
            println "OUTPUT: ${output.vcf}"
            println "COMMAND: $command"
            command = """
                echo "$inputs" > $output.vcf
            """
        }
        exec command, "gatk"
        forward output.vcf
    }
}
