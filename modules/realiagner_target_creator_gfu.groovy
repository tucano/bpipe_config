// MODULE REALIGNER CREATOR GFU
GATK="java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"

@intermediate
realiagner_target_creator_gfu =
{
    // stage vars
    var pretend          : false

    doc title: "Find small intervals with GATK toolkit: RealignerTargetCreator",
        desc: """
            Emits intervals for the Local Indel Realigner to target for realignment.
            Input: One BAM files and one or more lists of known indels.
            Output: A list of target intervals to pass to the Indel Realigner.
            stage options:
                pretend                : $pretend
                REFERENCE_GENOME_FASTA : $REFERENCE_GENOME_FASTA
                INTERVALS              : $INTERVALS
                DBSNP                  : $DBSNP
        """,
        constrains: "Require the index bai file for: $input.bam",
        author: "davide.rambaldi@gmail.com"

    transform("intervals")
    {
        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -I $input.bam
                  -R $REFERENCE_GENOME_FASTA
                  -T RealignerTargetCreator
                  -L $INTERVALS
                  -o $output.intervals
                  --unsafe ALLOW_SEQ_DICT_INCOMPATIBILITY
                  --known $DBSNP;
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
