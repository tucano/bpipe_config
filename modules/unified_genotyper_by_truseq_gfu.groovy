// MODULE UNIFIED GENOTYPER BY TRUSEQ INTERVALS GFU
import static groovy.io.FileType.*

@intermediate
unified_genotyper_by_truseq_gfu =
{
    var pretend        : false
    var call_conf      : 20.0
    var nct            : 4
    var glm            : "BOTH"
    var unsafe         : "ALLOW_SEQ_DICT_INCOMPATIBILITY"
    var rename         : ""
    var healty_exomes  : false
    var min_indel_frac : 0.2
    var with_groups    : true

    doc title: "GATK: Unified Genotyper",
        desc: """
            Produce a VCF file with a first call to SNP and INDELs.
            Parallelized in 10 jobs for chromosome.
            Inputs: ${inputs}, ${input.intervals}
            Output: ${input.bam.prefix}.${chr}.vcf
            With healty exomes: $healty_exomes

            CONFIGURATION:
                Reference       = $REFERENCE_GENOME_FASTA
                Inputs          = $inputs.bam
                DBSNP           = $DBSNP
                nct             = $nct
                stand_call_conf = $call_conf
                glm             = $glm
                Output          = ${input.bam.prefix}.${chr}.vcf
                region set      = ${input.intervals}
                Unsafe          = $unsafe
        """,
        constraints: "This stage use a file list for input bam. For security the list is removed in the next stage (vcf_concat)",
        author: "davide.rambaldi@gmail.com"

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

    def command_gatk = """
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
              -minIndelFrac $min_indel_frac
    """

    def output_prefix
    if (rename != "") {
        output_prefix = rename
    } else {
        output_prefix = "${input.bam.prefix}"
    }

    produce("${output_prefix}.${chr}.vcf","input_bams.${chr}.list")
    {
        def commands = []

        if (with_groups)
        {
            // generate a fixed number (10+1) of ranges with collate
            def ranges    = new File(input.intervals).text.split("\n").toList()
            def intervals = Math.floor(ranges.size / 10) as int
            def jobs      = []
            for (i in 0 .. 9) {
                if (i == 9) {
                    jobs << ranges
                } else {
                    jobs << ranges.take(intervals)
                    ranges = ranges.drop(intervals)
                }
            }


            jobs.eachWithIndex() { group, i ->
                def chr_intervals = group.collect { "-L $it"}.join(" ")
                commands << """
                    $command_gatk -o ${output_prefix}.${chr}.group_${i}.vcf ${chr_intervals}
                """
            }
        }


        if (pretend)
        {
            println "INPUT: $inputs"
            println "TEST MODE FOR CHROMOSOME $chr  [multi steps]"
            println "FINAL OUTPUT: $output"
            if (with_groups)
            {
                println "COMMAND LIST:"
                commands.each { com ->
                    println "COMMAND: $com"
                }
            }
            else
            {
                print "COMMAND: $command_gatk -o ${output.vcf} -L ${input.intervals};"
            }
            exec "touch $output"
        }
        else
        {
            if (with_groups)
            {
                // fixed with https://groups.google.com/forum/#!msg/bpipe-discuss/GQGTsrrAuDU/FSNcFTEYXosJ
                multiExec(commands)

                // CONCAT VCF FILES AND SORT
                exec """
                    $VCFCONCAT ${output_prefix}.${chr}.group_*.vcf | $VCFSORT > ${output_prefix}.${chr}.vcf;
                    rm ${output_prefix}.${chr}.group_*.vcf;
                    rm ${output_prefix}.${chr}.group_*.vcf.idx;
                ""","gatk"
            }
            else
            {
                exec """
                    $command_gatk -o ${output.vcf} -L ${input.intervals};
                """, "gatk"
            }
        }
    }
    forward output.vcf
}
