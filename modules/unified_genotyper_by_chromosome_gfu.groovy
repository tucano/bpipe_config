// MODULE UNIFIED GENOTYPER BT CHROMOSOME GFU
GATK = "java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"

@intermediate
unified_genotyper_by_chromosome_gfu = {
    var test      : false
    var call_conf : 20.0
    var nct       : 4
    var glm       : "BOTH"
    var unsafe    : "ALLOW_SEQ_DICT_INCOMPATIBILITY"

    doc title: "GATK: Unified Genotyper",
        desc: "Produce a VCF file with SNP calls and INDELs. Parallelized: 10 jobs for chromosome",
        author: "davide.rambaldi@gmail.com"

    def configuration = """
        [unified_genotyper_gfu]: UnifiedGenotyper configuration. Running with multi for single intervals.
        Inputs: ${input.bam}, ${input.intervals}
        Output: ${input.prefix}.${chr}.vcf
        CONFIGURATION:
            Reference       = $REFERENCE_GENOME_FASTA
            Input           = $input.bam
            DBSNP           = $DBSNP
            nct             = $nct
            stand_call_conf = $call_conf
            glm             = $glm
            Output          = ${input.prefix}.${chr}.vcf
            region set      = ${input.intervals}
            Unsafe          = $unsafe
    """.stripIndent()

    println configuration

    def command_gatk = """
        ulimit -l unlimited;
        ulimit -s unlimited;
        $GATK -R $REFERENCE_GENOME_FASTA
              -I $input.bam
              -T UnifiedGenotyper
              -nct $nct
              -stand_call_conf $call_conf
              -glm $glm
              -U $unsafe
              --interval_merging OVERLAPPING_ONLY;
    """.stripIndent().trim()

    produce("${input.prefix}.${chr}.vcf") {
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
        def commands = []
        jobs.each { group ->
            commands << group.collect { "-L $it"}.join(" ")
        }

        if (test) {
            println "INPUT: $input.bam"
            println "TEST MODE FOR CHROMOSOME $chr  [multi steps]"
            println "FINAL OUTPUT: $output"
            exec "touch $output"
        } else {
            // this weird things is because I can't generate multi in a loop
            multi """
                echo -e "[unified_genotyper_by_chromosome_gfu]: running UnifiedGenotyper of node $HOSTNAME with regions: ${commands[0]}";
                $command_gatk -o ${input.prefix}.${chr}.group_0.vcf ${commands[0]};
            """,
            """
                echo -e "[unified_genotyper_by_chromosome_gfu]: running UnifiedGenotyper of node $HOSTNAME with regions: ${commands[1]}";
                $command_gatk -o ${input.prefix}.${chr}.group_1.vcf ${commands[1]};
            """,
            """
                echo -e "[unified_genotyper_by_chromosome_gfu]: running UnifiedGenotyper of node $HOSTNAME with regions: ${commands[2]}";
                $command_gatk -o ${input.prefix}.${chr}.group_2.vcf ${commands[2]};
            """,
            """
                echo -e "[unified_genotyper_by_chromosome_gfu]: running UnifiedGenotyper of node $HOSTNAME with regions: ${commands[3]}";
                $command_gatk -o ${input.prefix}.${chr}.group_3.vcf ${commands[3]};
            """,
            """
                echo -e "[unified_genotyper_by_chromosome_gfu]: running UnifiedGenotyper of node $HOSTNAME with regions: ${commands[4]}";
                $command_gatk -o ${input.prefix}.${chr}.group_4.vcf ${commands[4]};
            """,
            """
                echo -e "[unified_genotyper_by_chromosome_gfu]: running UnifiedGenotyper of node $HOSTNAME with regions: ${commands[5]}";
                $command_gatk -o ${input.prefix}.${chr}.group_5.vcf ${commands[2]};
            """,
            """
                echo -e "[unified_genotyper_by_chromosome_gfu]: running UnifiedGenotyper of node $HOSTNAME with regions: ${commands[6]}";
                $command_gatk -o ${input.prefix}.${chr}.group_6.vcf ${commands[6]};
            """,
            """
                echo -e "[unified_genotyper_by_chromosome_gfu]: running UnifiedGenotyper of node $HOSTNAME with regions: ${commands[7]}";
                $command_gatk -o ${input.prefix}.${chr}.group_7.vcf ${commands[7]};
            """,
            """
                echo -e "[unified_genotyper_by_chromosome_gfu]: running UnifiedGenotyper of node $HOSTNAME with regions: ${commands[8]}";
                $command_gatk -o ${input.prefix}.${chr}.group_8.vcf ${commands[8]};
            """,
            """
                echo -e "[unified_genotyper_by_chromosome_gfu]: running UnifiedGenotyper of node $HOSTNAME with regions: ${commands[9]}";
                $command_gatk -o ${input.prefix}.${chr}.group_9.vcf ${commands[9]};
            """

            // CONCAT VCF FILES AND SORT
            // exec "touch ${input.prefix}.${chr}.vcf ${input.prefix}.${chr}.vcf.idx","gatk"
            exec """
                $VCFCONCAT ${input.prefix}.${chr}.group_*.vcf | vcf-sort > ${input.prefix}.${chr}.vcf;
                rm ${input.prefix}.${chr}.group_*.vcf;
                rm ${input.prefix}.${chr}.group_*.vcf.idx;
            ""","gatk"
        }
    }
    forward output.vcf
}
