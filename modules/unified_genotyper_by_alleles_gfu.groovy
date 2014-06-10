// MODULE UNIFIED GENOTYPER BY ALLELES (rev1)

import static groovy.io.FileType.*

@intermediate
unified_genotyper_by_alleles_gfu =
{
    var pretend        : false
    var call_conf      : 20.0
    var nct            : 16
    var glm            : "BOTH"
    var unsafe         : "ALLOW_SEQ_DICT_INCOMPATIBILITY"
    var rename         : ""
    var min_indel_frac : 0.2

    doc title: "GATK: Unified Genotyper by alleles",
        desc: "Produce a VCF file with SNP calls and INDELs. Parallelized in 1 job for chromosome",
        author: "davide.rambaldi@gmail.com"

    requires REFERENCE_GENOME_FASTA: "Please define a REFERENCE_GENOME_FASTA"
    requires GATK: "Please define GATK path"
    requires ALLELES: "Please define an alleles file (ALLELES)"
    requires DBSNP; "Please define a DBSNP file"

    def output_prefix
    if (rename != "") {
        output_prefix = rename
    } else {
        output_prefix = "${input.bam.prefix}"
    }

    def bam_list = new File("input_bams.${chr}.list")
    if (!bam_list.exists())
    {
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
                  --alleles $ALLELES
                  -gt_mode GENOTYPE_GIVEN_ALLELES
                  -out_mode EMIT_ALL_SITES
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
