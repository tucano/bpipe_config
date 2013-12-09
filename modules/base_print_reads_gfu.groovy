// MODULE BASE RECALIBRATOR GFU
GATK="java -Djava.io.tmpdir=/lustre2/scratch/ -Xmx32g -jar /lustre1/tools/bin/GenomeAnalysisTK.jar"

@preserve
base_print_reads_gfu = {
    // stage vars
    var ref_genome_fasta : "/lustre1/genomes/hg19/fa/hg19.fa"
    var truseq : "/lustre1/genomes/hg19/annotation/TruSeq_10k.intervals"
    var test : false

    doc title: "Base recalibration with GATK: generate a new recalibrated BAM file",
        desc: "Generate BAM file after recalibration with PrintReads",
        author: "davide.rambaldi@gmail.com"

    filter("recalibrated") {
        def command = """
            ulimit -l unlimited;
            ulimit -s unlimited;
            $GATK -R $ref_genome_fasta
                  -I $input.bam
                  -o $output.bam
                  -T PrintReads
                  -L $truseq
                  -nct 64
                  -BQSR $input.grp
        """

        if (test) {
            println "INPUT $input, OUTPUT: $output"
            println "COMMAND: $command"
            command = "touch $output"
        }
        exec command, "gatk"
    }
}
