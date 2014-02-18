// MODULE BASE RECALIBRATOR GFU
GATK="java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"

@preserve
base_print_reads_gfu =
{
    // stage vars
    var ref_genome_fasta : "/lustre1/genomes/hg19/fa/hg19.fa"
    var truseq           : "/lustre1/genomes/hg19/annotation/TruSeq_10k.intervals"
    var pretend          : false
    var nct              : 4

    doc title: "Base recalibration with GATK: generate a new recalibrated BAM file",
        desc: """
            Generate BAM file after recalibration with PrintReads.
            Inputs: ${input.bam} and ${input.grp}.

            Main options with value:
            pretend          : $pretend
            truseq           : $truseq
            ref_genome_fasta : $ref_genome_fasta
            nct              : $nct
        """,
        constraints: "",
        author: "davide.rambaldi@gmail.com"

    filter("recalibrated")
    {
        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -R $ref_genome_fasta
                  -I $input.bam
                  -o $output.bam
                  -T PrintReads
                  -L $truseq
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
