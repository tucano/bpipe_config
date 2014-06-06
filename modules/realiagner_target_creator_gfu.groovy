// MODULE REALIGNER CREATOR GFU

@intermediate
realiagner_target_creator_gfu =
{
    // stage vars
    var pretend          : false
    var target_intervals : false

    doc title: "Find small intervals with GATK toolkit: RealignerTargetCreator",
        desc: """
            Emits intervals for the Local Indel Realigner to target for realignment.
            Input: One BAM files and one or more lists of known indels.
            Output: A list of target intervals to pass to the Indel Realigner.
            stage options:
                pretend                : $pretend
                REFERENCE_GENOME_FASTA : $REFERENCE_GENOME_FASTA
                With target_intervals  : $target_intervals
                INTERVALS              : $INTERVALS
                DBSNP                  : $DBSNP
        """,
        constrains: "Require the index bai file for: ${input.bam}, forward the bam file to the next stage",
        author: "davide.rambaldi@gmail.com"

    transform("intervals")
    {
        def intervals_string = target_intervals ? "-L $INTERVALS" : ""

        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -I $input.bam
                  -R $REFERENCE_GENOME_FASTA
                  -T RealignerTargetCreator $intervals_string
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
