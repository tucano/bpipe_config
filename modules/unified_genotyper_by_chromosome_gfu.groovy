// MODULE UNIFIED GENOTYPER BY CHROMOSOME GFU
import static groovy.io.FileType.*

@intermediate
unified_genotyper_by_chromosome_gfu =
{
    var pretend        : false
    var call_conf      : 20.0
    var nct            : 16
    var glm            : "BOTH"
    var unsafe         : "ALLOW_SEQ_DICT_INCOMPATIBILITY"
    var rename         : ""
    var healty_exomes  : false
    var min_indel_frac : 0.2

    doc title: "GATK: Unified Genotyper",
        desc: """
            Produce a VCF file with SNP calls and INDELs. Parallelized in 1 job for chromosome
            Inputs: ${inputs}
            Output: ${output.vcf}
            With healty exomes: $healty_exomes

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
        """,
        author: "davide.rambaldi@gmail.com"

    def output_prefix
    if (rename != "") {
        output_prefix = rename
    } else {
        output_prefix = "${input.bam.prefix}"
    }

    def bam_list = new File("input_bams.${chr}.list")
    if (!bam_list.exists())
    {
        if (healty_exomes)
        {
            new File("$HEALTY_EXOMES_DIR").eachFileMatch FILES, ~/.*\.bam/, { bam ->
                bam_list << bam << "\n"
            }
        }
        inputs.bam.each { bam ->
            bam_list << bam << "\n"
        }
    }

    produce("${output_prefix}.${chr}.vcf","input_bams.${chr}.list")
    {
         def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -R $REFERENCE_GENOME_FASTA
                  -I input_bams.${chr}.list
                  --dbsnp $DBSNP
                  -T UnifiedGenotyper
                  -nct $nct
                  -stand_call_conf $call_conf
                  -glm $glm
                  -U $unsafe
                  -L $chr
                  -o $output.vcf
                  -minIndelFrac $min_indel_frac
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
