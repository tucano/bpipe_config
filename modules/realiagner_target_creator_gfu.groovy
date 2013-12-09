// MODULE REALIGNER CREATOR GFU
GATK="java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK-2.8-1.jar"

@intermediate
realiagner_target_creator_gfu = {
    // stage vars
    var ref_genome_fasta : "/lustre1/genomes/hg19/fa/hg19.fa"
    var truseq : "/lustre1/genomes/hg19/annotation/TruSeq_10k.intervals"
    var dbsnp : "/lustre1/genomes/hg19/annotation/dbSNP-137.chr.vcf"
    var test : false

    doc title: "Find small intervals with GATK toolkit: RealignerTargetCreator",
        desc: "Find small intervals that should be realigned",
        constrains: "Require the index bai file for bam: $input",
        author: "davide.rambaldi@gmail.com"

    transform("intervals") {
        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -I $input.bam
                  -R $ref_genome_fasta
                  -T RealignerTargetCreator
                  -L $truseq
                  -o $output.intervals
                  --unsafe ALLOW_SEQ_DICT_INCOMPATIBILITY
                  --known $dbsnp;
        """
        if (test) {
            println "INPUT $input, OUTPUT: $output"
            println "COMMAND: $command"
            command = "touch $output"
        }
        exec command, "gatk"
    }
    forward input.bam
}
