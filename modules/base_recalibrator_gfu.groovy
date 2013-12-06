// MODULE BASE RECALIBRATOR GFU
GATK="java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"

@intermediate
base_recalibrator_gfu = {
    // stage vars
    var ref_genome_fasta : "/lustre1/genomes/hg19/fa/hg19.fa"
    var dbsnp            : "/lustre1/genomes/hg19/annotation/dbSNP-137.chr.vcf"
    var truseq           : "/lustre1/genomes/hg19/annotation/TruSeq_10k.intervals"
    var test             : false

    doc title: "Base recalibration with GATK",
        desc: "Base recalibration with GATK tool: BaseRecalibrator",
        author: "davide.rambaldi@gmail.com"

    transform("grp") {
        def custom_output
        println "${inputs.toList()}"
        if (inputs.toList().size > 1) {
            custom_output = "${PROJECTNAME}.grp"
            println "Renanimng GRP file with project name: "
        } else {
            custom_output = "$output.grp"
        }

        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -R $ref_genome_fasta
                  -knownSites $dbsnp
                  ${inputs.bam.collect{ "-I $it" }.join(" ")}
                  -L $truseq
                  -T BaseRecalibrator
                  --covariate QualityScoreCovariate
                  --covariate CycleCovariate
                  --covariate ContextCovariate
                  --covariate ReadGroupCovariate
                  --unsafe ALLOW_SEQ_DICT_INCOMPATIBILITY
                  -nct 64
                  -o $custom_output
        """

        if (test) {
            println "INPUT $input, OUTPUT: $output"
            println "COMMAND: $command"
            command = "touch $custom_output"
        }

        exec command, "gatk"
        forward custom_output
    }
}
