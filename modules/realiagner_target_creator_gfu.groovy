// MODULE REALIGNER CREATOR GFU
GATK="java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"

@intermediate
realiagner_target_creator_gfu = 
{
    // stage vars
    var ref_genome_fasta : "/lustre1/genomes/hg19/fa/hg19.fa"
    var truseq           : "/lustre1/genomes/hg19/annotation/TruSeq_10k.intervals"
    var dbsnp            : "/lustre1/genomes/hg19/annotation/dbSNP-137.chr.vcf"
    var pretend          : false

    doc title: "Find small intervals with GATK toolkit: RealignerTargetCreator",
        desc: """
            Emits intervals for the Local Indel Realigner to target for realignment.
            Input: One BAM files and one or more lists of known indels.
            Output: A list of target intervals to pass to the Indel Realigner.
        """,
        constrains: "Require the index bai file for bam: $input",
        author: "davide.rambaldi@gmail.com"

    transform("intervals") 
    {
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
        exec command, "gatk"
    }
    forward input.bam
}
