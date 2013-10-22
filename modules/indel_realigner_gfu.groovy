// MODULE INDEL REALIGNER GFU
GATK="java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"

@Filter("indel_realigned")
indel_realigner_gfu = {
    // stage vars
    var ref_genome_fasta : "/lustre1/genomes/hg19/fa/hg19.fa"
    var truseq : "/lustre1/genomes/hg19/annotation/TruSeq_10k.intervals"
    var dbsnp : "/lustre1/genomes/hg19/annotation/dbSNP-137.chr.vcf"

    doc title: "GFU Realign reads marked by stage realiagner_target_creator_gfu with GATK toolkit: IndelRealigner",
        desc: "Realign small intervals marked by GATK: RealignerTargetCreator",
        author: "davide.rambaldi@gmail.com"

    exec"""
        ulimit -l unlimited;
        ulimit -s unlimited;
        $GATK -I $input.bam
              -R $ref_genome_fasta
              -T IndelRealigner
              -L $truseq
              -targetIntervals $input.intervals
              -o $output.bam
              --unsafe ALLOW_SEQ_DICT_INCOMPATIBILITY
              -known $dbsnp;
      ""","gatk"

    forward input.bam
}