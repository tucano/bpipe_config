// MODULE INDEL REALIGNER GFU
GATK="java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"

@intermediate
indel_realigner_gfu =
{
    // stage vars
    var pretend          : false

    doc title: "Realign reads marked by stage realiagner_target_creator_gfu with GATK toolkit: IndelRealigner",
        desc: """
            Realign small intervals marked by GATK: RealignerTargetCreator.
            stage options with value:
                pretend                : $pretend
                DBSNP                  : $DBSNP
                INTERVALS              : $INTERVALS
                REFERENCE_GENOME_FASTA : $REFERENCE_GENOME_FASTA
        """,
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"


    filter("indel_realigned")
    {
        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -I $input.bam
                  -R $REFERENCE_GENOME_FASTA
                  -T IndelRealigner
                  -L $INTERVALS
                  -targetIntervals $input.intervals
                  -o $output.bam
                  --unsafe ALLOW_SEQ_DICT_INCOMPATIBILITY
                  -known $DBSNP;
        """

        if (pretend)
        {
            println """
                INPUTS: $input.bam $input.intervals
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
