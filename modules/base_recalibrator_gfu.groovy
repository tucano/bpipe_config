// MODULE BASE RECALIBRATOR GFU
GATK="java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"

@intermediate
base_recalibrator_gfu = {
    // stage vars
    var ref_genome_fasta : "/lustre1/genomes/hg19/fa/hg19.fa"
    var dbsnp            : "/lustre1/genomes/hg19/annotation/dbSNP-137.chr.vcf"
    var truseq           : "/lustre1/genomes/hg19/annotation/TruSeq_10k.intervals"

    doc title: "GFU Base recalibration with GATK",
        desc: "Base recalibration with GATK tool: BaseRecalibrator",
        author: "davide.rambaldi@gmail.com"

    transform("grp") {
      def command = """
        ulimit -l unlimited;
        ulimit -s unlimited;
        $GATK -R $ref_genome_fasta
              -knownSites $dbsnp
              -I $input.bam
              -L $truseq
              -T BaseRecalibrator
              --covariate QualityScoreCovariate
              --covariate CycleCovariate
              --covariate ContextCovariate
              --covariate ReadGroupCovariate
              --unsafe ALLOW_SEQ_DICT_INCOMPATIBILITY
              -nct 64
              -o $output.grp
      """

        if (test) {
            println "INPUT $input, OUTPUT: $ouptut"
            println "COMMAND: $command"
            command = "touch $output"
        }
        exec command, "gatk"
    }
    forward input.bam
}
