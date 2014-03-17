// MODULE BASE RECALIBRATOR GFU
GATK="java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"

@preserve
base_print_reads_gfu =
{
    // stage vars
    var pretend          : false
    var nct              : 4
    var target_intervals : false

    doc title: "Base recalibration with GATK: generate a new recalibrated BAM file",
        desc: """
            Generate BAM file after recalibration with PrintReads.
            Inputs: ${input.bam} and ${input.grp}.

            Main options with value:
            pretend                : $pretend
            With target_intervals  : $target_intervals
            INTERVALS              : $INTERVALS
            REFERENCE_GENOME_FASTA : $REFERENCE_GENOME_FASTA
            nct                    : $nct
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    filter("recalibrated")
    {
        def intervals_string = target_intervals ? "-L $INTERVALS" : ""
        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -R $REFERENCE_GENOME_FASTA
                  -I $input.bam
                  -o $output.bam
                  -T PrintReads $intervals_string
                  -nct $nct
                  -BQSR $input.grp
        """

        if (pretend) {
            println """
                INPUT BAM:  $input.bam
                INPUT GRP:  $input.grp
                OUTPUT:     $output
                COMMAND:
                    $command
            """
            command = """
                echo "INPUTS: $input.bam $input.grp" > $output;
            """
        }
        exec command, "gatk"
    }
}
