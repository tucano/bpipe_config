// MODULE INDEL REALIGNER GFU

@intermediate
indel_realigner_gfu =
{
    // stage vars
    var pretend          : false
    var target_intervals : false

    doc title: "Realign reads marked by stage realiagner_target_creator_gfu with GATK toolkit: IndelRealigner",
        desc: """
            Realign small intervals marked by GATK: RealignerTargetCreator.
            stage options with value:
                pretend                : $pretend
                DBSNP                  : $DBSNP
                With target_intervals  : $target_intervals
                INTERVALS              : $INTERVALS
                REFERENCE_GENOME_FASTA : $REFERENCE_GENOME_FASTA
        """,
        constraints: " ... ",
        author: "davide.rambaldi@gmail.com"


    filter("indel_realigned")
    {
        def intervals_string = target_intervals ? "-L $INTERVALS" : ""

        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -I $input.bam
                  -R $REFERENCE_GENOME_FASTA
                  -T IndelRealigner $intervals_string
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
