// MODULE INDEL REALIGNER GFU
GATK="java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"

@intermediate
indel_realigner_gfu = 
{
    // stage vars
    var ref_genome_fasta : "/lustre1/genomes/hg19/fa/hg19.fa"
    var truseq           : "/lustre1/genomes/hg19/annotation/TruSeq_10k.intervals"
    var dbsnp            : "/lustre1/genomes/hg19/annotation/dbSNP-137.chr.vcf"
    var pretend          : false

    doc title: "Realign reads marked by stage realiagner_target_creator_gfu with GATK toolkit: IndelRealigner",
        desc: """
            Realign small intervals marked by GATK: RealignerTargetCreator.

            stage options with value:
                pretend          : $pretend
                dbsnp            : $dbsnp
                truseq           : $truseq
                ref_genome_fasta : $ref_genome_fasta
        """,
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"


    filter("indel_realigned") 
    {
        def command = """
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
        """

        if (pretend) 
        {
            println """
                INPUT $input
                OUTPUT: $output
                COMMAND: $command
            """
            
            command = """ 
                echo "INPUT: $input" > $output
            """
        }
        exec command,"gatk"
    }
}
