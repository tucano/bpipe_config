// MODULE UNIFIED GENOTYPER BY CHROMOSOME GFU
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
        Inputs: ${inputs}, ${input.intervals}
        Output: ${input.bam.prefix}.${chr}.vcf
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
    """.stripIndent()

    println configuration

    def command_gatk = """
        ulimit -l unlimited;
        ulimit -s unlimited;
        $GATK -R $REFERENCE_GENOME_FASTA
              ${inputs.bam.collect{ "-I $it" }.join(" ")}
              -T UnifiedGenotyper
              -nct $nct
              -stand_call_conf $call_conf
              -glm $glm
              -U $unsafe
              --interval_merging OVERLAPPING_ONLY
    """.stripIndent().trim()

    produce("${input.bam.prefix}.${chr}.vcf") {
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
        jobs.eachWithIndex() { group, i ->
            def chr_intervals = group.collect { "-L $it"}.join(" ")
            commands << """
                echo -e "[unified_genotyper_by_chromosome_gfu]: running UnifiedGenotyper of node $HOSTNAME with chromosome $chr and ${group.size} regions";
                $command_gatk -o ${input.bam.prefix}.${chr}.group_${i}.vcf ${chr_intervals}
            """
        }

        if (test) {
            println "INPUT: $input.bam"
            println "TEST MODE FOR CHROMOSOME $chr  [multi steps]"
            println "FINAL OUTPUT: $output"
            println "COMMAND LIST:"
            commands.each { com ->
                println "COMMAND: $com"
            }
            exec "touch $output"
        } else {
            // fixed with https://groups.google.com/forum/#!msg/bpipe-discuss/GQGTsrrAuDU/FSNcFTEYXosJ
            multiExec(commands)

            // CONCAT VCF FILES AND SORT
            // exec "touch ${input.prefix}.${chr}.vcf ${input.prefix}.${chr}.vcf.idx","gatk"
            exec """
                $VCFCONCAT ${input.bam.prefix}.${chr}.group_*.vcf | vcf-sort > ${input.bam.prefix}.${chr}.vcf;
                rm ${input.bam.prefix}.${chr}.group_*.vcf;
                rm ${input.bam.prefix}.${chr}.group_*.vcf.idx;
            ""","gatk"
        }
    }
    forward output.vcf
}
